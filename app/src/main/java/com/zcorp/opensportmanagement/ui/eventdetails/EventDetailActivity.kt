package com.zcorp.opensportmanagement.ui.eventdetails

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.view.View
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.Match
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.EventInformationFragment
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.EventPlayersFragment
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.IEventDetailView
import kotlinx.android.synthetic.main.activity_event_detail.*


class EventDetailActivity : BaseActivity(), IEventDetailView {


    private val mBottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_event_details_info -> {
                transaction.replace(R.id.fragment_event_details_container, EventInformationFragment())
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_event_details_players -> {
                transaction.replace(R.id.fragment_event_details_container, EventPlayersFragment())
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        super.mActivityComponent.inject(this)
        val event = intent.getSerializableExtra("event") as Event
        setSupportActionBar(toolbar_event_details)
        event_detail_subscription_fab.setOnClickListener { view ->
            ThemedSnackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (event is Match) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
        } else {
            layout_match_description.visibility = View.GONE
            supportActionBar?.title = event.name
        }
        event_detail_navigation.setOnNavigationItemSelectedListener(mBottomNavigationListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_event_details_container, EventInformationFragment())
        transaction.commit()
    }
}
