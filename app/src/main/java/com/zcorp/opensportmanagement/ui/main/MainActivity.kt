package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsFragment
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsFragment
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsViewModel
import com.zcorp.opensportmanagement.utils.log.ILogger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MainActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    @Inject lateinit var eventsFragment: EventsFragment
    @Inject lateinit var conversationsFragment: ConversationsFragment
    @Inject lateinit var mLogger: ILogger
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel


    private lateinit var mEventsViewModel: EventsViewModel

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
        super.mActivityComponent.inject(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar as Toolbar)
        main_navigation.setOnNavigationItemSelectedListener(mBottomNavigationListener)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mEventsViewModel = ViewModelProviders.of(this, viewModelFactory).get(EventsViewModel::class.java)

        mainViewModel.states.observe(this, Observer { state ->
            when (state) {
                is State.Success -> {
                    val spinnerArrayAdapter = ArrayAdapter<String>(
                            this, R.layout.simple_spinner_item, state.data.map { it.name })
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    main_spinner.adapter = spinnerArrayAdapter
                    main_spinner.onItemSelectedListener = this
                    displayEvents()
                }
            }
        })
        mainViewModel.getTeams()
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
