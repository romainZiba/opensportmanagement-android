package com.zcorp.opensportmanagement.screens.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.screens.main.fragments.ButtonFragment.PlusOneFragment
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val mainPresenter = MainPresenterImpl(this)

    override fun displayEvents() {
        val transaction = fragmentManager.beginTransaction()
        val plusOneFragment = fragmentManager.findFragmentByTag(PLUS_ONE)
        if (plusOneFragment != null) {
            transaction.hide(plusOneFragment)
        }
        var eventsFragment = fragmentManager.findFragmentByTag(EVENTS)
        if (eventsFragment == null) {
            eventsFragment = EventFragment()
            transaction.add(R.id.fragment_container, eventsFragment, EVENTS)
        } else {
            transaction.show(eventsFragment)
        }
        transaction.commit()
    }

    override fun displayGoogle() {
        val transaction = fragmentManager.beginTransaction()
        val eventsFragment = fragmentManager.findFragmentByTag(EVENTS)
        if (eventsFragment != null) {
            transaction.hide(eventsFragment)
        }
        var plusOneFragment = fragmentManager.findFragmentByTag(PLUS_ONE)
        if (plusOneFragment == null) {
            plusOneFragment = PlusOneFragment()
            transaction.add(R.id.fragment_container, plusOneFragment, PLUS_ONE)
        } else {
            transaction.show(plusOneFragment)
        }
        transaction.commit()
    }

    override fun displayThirdFragment() {
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar as Toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mainPresenter.onViewReady()
    }

    companion object {
        const val EVENTS = "EVENTS"
        const val PLUS_ONE = "PLUS_ONE"
    }
}
