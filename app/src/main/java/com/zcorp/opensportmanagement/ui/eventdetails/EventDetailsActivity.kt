package com.zcorp.opensportmanagement.ui.eventdetails

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.view.View
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.Information.EventInformationFragment
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.members.EventMembersFragment
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.match_description_layout.*
import javax.inject.Inject


class EventDetailsActivity : BaseActivity(), IEventDetailsView {

    @Inject
    lateinit var mPresenter: IEventDetailsPresenter

    @Inject
    lateinit var mEventInformationFragment: EventInformationFragment

    @Inject
    lateinit var mEventMembersFragment: EventMembersFragment

    override fun displayInformation() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_event_details_container, mEventInformationFragment)
        transaction.commit()
    }

    override fun displayTeamMembers() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_event_details_container, mEventMembersFragment)
        transaction.commit()
    }

    private val mBottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_event_details_info -> {
                mPresenter.onDisplayInformation()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_event_details_players -> {
                mPresenter.onDisplayTeamMembers()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        super.mActivityComponent.inject(this)
        val event = intent.getSerializableExtra("event") as Event
        setSupportActionBar(toolbar_event_details)
        event_detail_subscription_fab.setOnClickListener { view ->
            ThemedSnackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (event.isMatch()) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            mPresenter.getMatchDetails(event._id)
        } else {
            layout_match_description.visibility = View.GONE
            supportActionBar?.title = event.name
            mPresenter.getEventDetails(event._id)
        }
        event_details_navigation.setOnNavigationItemSelectedListener(mBottomNavigationListener)
        mPresenter.onAttach(this)
        displayInformation()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }
}
