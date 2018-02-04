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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = fragmentManager.beginTransaction()
        val currentFragment = fragmentManager.findFragmentById(R.id.fragment_container)
        transaction.hide(currentFragment)
        when (item.itemId) {
            R.id.navigation_events -> {
                var fragment = fragmentManager.findFragmentByTag("EVENTS")
                if (fragment == null) {
                    fragment = EventFragment()
                    transaction.add(R.id.fragment_container, fragment, "EVENTS")
                } else {
                    transaction.show(fragment)
                }
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_google -> {
                var fragment = fragmentManager.findFragmentByTag("PLUS_ONE")
                if (fragment == null) {
                    fragment = PlusOneFragment()
                    transaction.add(R.id.fragment_container, fragment, "PLUS_ONE")
                } else {
                    transaction.show(fragment)
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
        transaction.replace(R.id.fragment_container, EventFragment()).commit()
    }
}
