package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.Observer
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.Toolbar
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
import kotlinx.android.synthetic.main.activity_main.cl_main
import kotlinx.android.synthetic.main.activity_main.main_navigation
import kotlinx.android.synthetic.main.activity_main.main_toolbar
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private val eventsFragment = EventsFragment()
    private val conversationsFragment = ConversationsFragment()
    private val teamDetailsFragment = TeamDetailsFragment()
    private val viewModel: MainViewModel by viewModel()
    private var availableTeams: List<TeamEntity> = listOf()
    private val mPreferencesHelper: PreferencesHelper by inject()
    private val networkChangesListener = NetworkChangesListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar as Toolbar)
        main_navigation.setOnNavigationItemSelectedListener(mBottomNavigationListener)

        viewModel.connectivityStates.observe(this, Observer { state ->
            when (state) {
                ConnectivityState.CONNECTED -> {
                    Snackbar.make(cl_main, getString(R.string.connection_retrieved), Snackbar.LENGTH_SHORT).show()
                    viewModel.getUserInformation()
                }
                else -> Snackbar.make(cl_main, getString(R.string.no_connection), Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.loggedState.observe(this, Observer { state ->
            when (state) {
                is State.Failure -> {
                    Snackbar.make(cl_main, getString(R.string.not_logged), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.login)) { showLoginDialog() }
                            .show()
                }
                else -> {
                }
            }
            viewModel.getTeams()
        })

        viewModel.teams.observe(this, Observer { state ->
            when (state) {
                is State.SuccessFromDb -> handleTeams(state.data)
                is State.Success -> handleTeams(state.data)
                is State.Failure -> {
                    Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.getConnectivityStates()
        displayEvents()
        this.registerReceiver(networkChangesListener, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onDestroy() {
        super.onDestroy()
        this.unregisterReceiver(networkChangesListener)
    }

    private fun handleTeams(teams: List<TeamEntity>) {
        availableTeams = teams
        if (availableTeams.isNotEmpty()) {
            if (mPreferencesHelper.getCurrentTeamId() == -1) {
                showTeamPickerDialog(availableTeams)
            } else {
                onTeamSelected()
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

    private fun onTeamSelected() {
        eventsFragment.onTeamSelected()
    }

    private val mBottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_events -> {
                displayEvents()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                displayConversations()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_team -> {
                displayTeamDetails()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun displayFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, "")
        transaction.commit()
    }

    private fun displayEvents() {
        displayFragment(eventsFragment)
    }

    private fun displayConversations() {
        displayFragment(conversationsFragment)
    }

    private fun displayTeamDetails() {
        displayFragment(teamDetailsFragment)
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
                    mPreferencesHelper.setCurrentTeamId(selected._id)
                    onTeamSelected()
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
