package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsFragment
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsFragment
import com.zcorp.opensportmanagement.utils.log.ILogger
import kotlinx.android.synthetic.main.activity_main.fragment_container
import kotlinx.android.synthetic.main.activity_main.main_navigation
import kotlinx.android.synthetic.main.activity_main.main_toolbar
import kotlinx.android.synthetic.main.toolbar.main_spinner
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    private val eventsFragment = EventsFragment()
    private val conversationsFragment = ConversationsFragment()
    private val mLogger: ILogger by inject()

    private val viewModel: MainViewModel by viewModel()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar as Toolbar)
        main_navigation.setOnNavigationItemSelectedListener(mBottomNavigationListener)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        viewModel.states.observe(this, Observer { state ->
            when (state) {
                is State.Success -> {
                    val spinnerArrayAdapter = ArrayAdapter<String>(
                            this, R.layout.simple_spinner_item, state.data.map { it.name })
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    main_spinner.adapter = spinnerArrayAdapter
                    main_spinner.onItemSelectedListener = this
                    displayEvents()
                }
                is State.Failure -> {
                    ThemedSnackbar
                            .make(fragment_container, getString(R.string.load_teams_error), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.retry)) { viewModel.getTeams() }
                }
            }
        })
        viewModel.getTeams()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return false
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        mLogger.d(TAG, "No team selected")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        mLogger.d(TAG, "team selected")
    }
}
