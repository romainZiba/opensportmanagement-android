package com.zcorp.opensportmanagement.ui.main

import android.app.FragmentTransaction
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.Toolbar
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.main.fragments.button.PlusOneFragment
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsFragment
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), IMainView {

    @Inject
    lateinit var mainPresenter: IMainPresenter

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
                mainPresenter.onDisplayThirdFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun displayEvents() {
        val transaction = fragmentManager.beginTransaction()
        hideFragmentWithTag(transaction, PLUS_ONE)
        hideFragmentWithTag(transaction, CONVERSATIONS)
        var eventsFragment = fragmentManager.findFragmentByTag(EVENTS)
        if (eventsFragment == null) {
            eventsFragment = EventsFragment()
            transaction.add(R.id.fragment_container, eventsFragment, EVENTS)
        } else {
            transaction.show(eventsFragment)
        }
        transaction.commit()
    }

    override fun displayGoogle() {
        val transaction = fragmentManager.beginTransaction()
        hideFragmentWithTag(transaction, EVENTS)
        hideFragmentWithTag(transaction, CONVERSATIONS)
        var plusOneFragment = fragmentManager.findFragmentByTag(PLUS_ONE)
        if (plusOneFragment == null) {
            plusOneFragment = PlusOneFragment()
            transaction.add(R.id.fragment_container, plusOneFragment, PLUS_ONE)
        } else {
            transaction.show(plusOneFragment)
        }
        transaction.commit()
    }

    override fun displayConversations() {
        val transaction = fragmentManager.beginTransaction()
        hideFragmentWithTag(transaction, EVENTS)
        hideFragmentWithTag(transaction, PLUS_ONE)
        var conversationsFragment = fragmentManager.findFragmentByTag(CONVERSATIONS)
        if (conversationsFragment == null) {
            conversationsFragment = ConversationsFragment()
            transaction.add(R.id.fragment_container, conversationsFragment, CONVERSATIONS)
        } else {
            transaction.show(conversationsFragment)
        }
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mActivityComponent.inject(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar as Toolbar)
        mainPresenter.onAttach(this)
        navigation.setOnNavigationItemSelectedListener(mBottomNavigationListener)
    }

    private fun hideFragmentWithTag(transaction: FragmentTransaction, tag: String) {
        val fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            transaction.hide(fragment)
        }
    }

    companion object {
        const val EVENTS = "EVENTS"
        const val PLUS_ONE = "PLUS_ONE"
        const val CONVERSATIONS = "CONVERSATIONS"
    }
}
