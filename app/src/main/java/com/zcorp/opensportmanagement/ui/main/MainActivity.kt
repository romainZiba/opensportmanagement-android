package com.zcorp.opensportmanagement.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsFragment
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), IMainView {

    @Inject
    lateinit var mainPresenter: IMainPresenter

    @Inject
    lateinit var eventsFragment: EventsFragment

    @Inject
    lateinit var conversationsFragment: ConversationsFragment

    private var mVisibleFragment = EVENTS

    private val mBottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_events -> {
                mainPresenter.onDisplayEvents()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_google -> {
                mainPresenter.onDisplayGoogle()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mainPresenter.onDisplayConversations()
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

    override fun displayEvents() {
        displayFragment(eventsFragment)
        mVisibleFragment = EVENTS
    }

    override fun displayGoogle() {
        displayFragment(eventsFragment)
        mVisibleFragment = PLUS_ONE
    }

    override fun displayConversations() {
        displayFragment(conversationsFragment)
        mVisibleFragment = CONVERSATIONS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mActivityComponent.inject(this)
        mVisibleFragment = savedInstanceState?.getString(FRAGMENT_KEY) ?: EVENTS
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar as Toolbar)
        mainPresenter.onAttach(this, mVisibleFragment)
        main_navigation.setOnNavigationItemSelectedListener(mBottomNavigationListener)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(FRAGMENT_KEY, mVisibleFragment)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.onDetach()
    }

    companion object {
        const val EVENTS = "EVENTS"
        const val PLUS_ONE = "PLUS_ONE"
        const val CONVERSATIONS = "CONVERSATIONS"
        const val FRAGMENT_KEY = "VISIBLE_FRAGMENT"
    }
}
