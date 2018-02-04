package com.zcorp.opensportmanagement.screens.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.screens.main.fragments.ButtonFragment.PlusOneFragment
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val EVENTS = "EVENTS"
        const val PLUS_ONE = "PLUS_ONE"
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = fragmentManager.beginTransaction()
        var eventsFragment = fragmentManager.findFragmentByTag(EVENTS)
        if (eventsFragment != null) {
            transaction.hide(eventsFragment)
        }
        var plusOneFragment = fragmentManager.findFragmentByTag(PLUS_ONE)
        if (plusOneFragment != null) {
            transaction.hide(plusOneFragment)
        }
        when (item.itemId) {
            R.id.navigation_events -> {
                if (eventsFragment == null) {
                    eventsFragment = EventFragment()
                    transaction.add(R.id.fragment_container, eventsFragment, EVENTS)
                } else {
                    transaction.show(eventsFragment)
                }
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_google -> {
                if (plusOneFragment == null) {
                    plusOneFragment = PlusOneFragment()
                    transaction.add(R.id.fragment_container, plusOneFragment, PLUS_ONE)
                } else {
                    transaction.show(plusOneFragment)
                }
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar as Toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, EventFragment(), EVENTS).commit()
    }
}
