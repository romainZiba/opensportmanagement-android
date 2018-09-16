package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.Observer
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.widget.AppCompatButton
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.zcorp.opensportmanagement.ConnectivityState
import com.zcorp.opensportmanagement.NetworkChangesListener
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.datasource.local.TeamEntity
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.conversations.ConversationsFragment
import com.zcorp.opensportmanagement.ui.events.EventsFragment
import com.zcorp.opensportmanagement.ui.team_details.TeamDetailsFragment
import com.zcorp.opensportmanagement.ui.user_profile.MyProfileFragment
import kotlinx.android.synthetic.main.activity_main.cl_main
import kotlinx.android.synthetic.main.activity_main.main_fab
import kotlinx.android.synthetic.main.activity_main.main_navigation
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val EVENTS_TAG = "events"
        const val TEAM_TAG = "team"
        const val MESSAGES_TAG = "messages"
        const val PROFILE_TAG = "profile"
        const val VISIBLE_FRAGMENT_KEY = "fragment"
    }

    private val eventsFragment = EventsFragment()
    private val conversationsFragment = ConversationsFragment()
    private val teamDetailsFragment = TeamDetailsFragment()
    private val myProfileFragment = MyProfileFragment()
    private val viewModel: MainViewModel by viewModel()
    private var availableTeams: List<TeamEntity> = listOf()
    private val mPreferencesHelper: PreferencesHelper by inject()
    private val networkChangesListener = NetworkChangesListener()
    private var visibleFragment: String = EVENTS_TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        main_navigation.setOnNavigationItemSelectedListener(mBottomNavigationListener)

        viewModel.connectivityStates.observe(this, Observer { state ->
            when (state) {
                ConnectivityState.CONNECTED -> {
                    Log.d(TAG, "User has data access.")
                    Snackbar.make(cl_main, getString(R.string.connection_retrieved), Snackbar.LENGTH_SHORT).show()
                    viewModel.getUserInformation()
                }
                else -> {
                    Log.d(TAG, "User does not have data access.")
                    Snackbar.make(cl_main, getString(R.string.error_no_connection), Snackbar.LENGTH_LONG).show()
                }
            }
        })

        viewModel.loggedState.observe(this, Observer { state ->
            when (state) {
                is State.Failure -> {
                    Log.d(TAG, "User is not logged. Trying to get teams data from db")
                    viewModel.getTeams(false)
                    Snackbar.make(cl_main, getString(R.string.error_not_logged), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.login)) { showLoginDialog() }
                            .show()
                }
                else -> {
                    Log.d(TAG, "User is logged. Trying to get fresh teams data")
                    viewModel.getTeams(true)
                }
            }
        })

        viewModel.teams.observe(this, Observer { state ->
            when (state) {
                is State.SuccessFromDb -> availableTeams = state.data
                is State.Success -> handleTeams(state.data)
                is State.Failure -> {
                    Toast.makeText(this, getString(R.string.error_retrieve_teams), Toast.LENGTH_LONG).show()
                }
            }
        })
        if (savedInstanceState == null) {
            displayFragment(EVENTS_TAG)
        } else {
            displayFragment(savedInstanceState.getString(VISIBLE_FRAGMENT_KEY, EVENTS_TAG))
        }
    }

    override fun onResume() {
        super.onResume()
        this.registerReceiver(networkChangesListener, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        viewModel.getConnectivityStates()
    }

    override fun onPause() {
        super.onPause()
        this.unregisterReceiver(networkChangesListener)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(VISIBLE_FRAGMENT_KEY, visibleFragment)
    }

    private fun handleTeams(teams: List<TeamEntity>) {
        availableTeams = teams
        if (availableTeams.isNotEmpty()) {
            val currentTeamId = mPreferencesHelper.getCurrentTeamId()
            if (currentTeamId == -1) {
                showTeamPickerDialog(availableTeams)
            } else {
                viewModel.selectTeam(currentTeamId)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.change_team -> {
                if (availableTeams.isNotEmpty()) {
                    showTeamPickerDialog(availableTeams)
                }
                return true
            }
        }
        return false
    }

    private val mBottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_events -> {
                main_fab.show()
                displayFragment(EVENTS_TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                main_fab.show()
                displayFragment(MESSAGES_TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_team -> {
                main_fab.show()
                displayFragment(TEAM_TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account_details -> {
                main_fab.hide()
                displayFragment(PROFILE_TAG)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun displayFragment(tag: String) {
        val fragment = when (tag) {
            MESSAGES_TAG -> conversationsFragment
            TEAM_TAG -> teamDetailsFragment
            PROFILE_TAG -> myProfileFragment
            else -> eventsFragment
        }
        visibleFragment = tag
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, tag)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_toolbar, menu)
        return true
    }

    private fun showTeamPickerDialog(teams: List<TeamEntity>) {
        MaterialDialog(this)
                .title(R.string.choose_team)
                .positiveButton(R.string.select)
                .listItemsSingleChoice(items = teams.map { team -> team.name }) { dialog, index, _ ->
                    val selected = teams[index]
                    val selectedTeamId = selected._id
                    mPreferencesHelper.setCurrentTeamId(selectedTeamId)
                    viewModel.selectTeam(selectedTeamId)
                    dialog.dismiss()
                }
                .show()
    }

    private fun showLoginDialog() {
        val loginDialog = MaterialDialog(this)
                .customView(R.layout.dialog_login)
        loginDialog.show()
        val usernameInput = loginDialog.findViewById<EditText>(R.id.et_dialog_login_username)
        val passwordInput = loginDialog.findViewById<EditText>(R.id.et_dialog_login_password)
        val loginBtn = loginDialog.findViewById<AppCompatButton>(R.id.btn_dialog_login_login)
        val cancelBtn = loginDialog.findViewById<AppCompatButton>(R.id.btn_dialog_login_cancel)
        loginBtn.setOnClickListener { _ ->
            loginDialog.dismiss()
            viewModel.login(username = usernameInput?.text.toString(),
                    password = passwordInput?.text.toString())
        }
        cancelBtn.setOnClickListener { _ -> loginDialog.dismiss() }
        loginDialog.show()
    }
}
