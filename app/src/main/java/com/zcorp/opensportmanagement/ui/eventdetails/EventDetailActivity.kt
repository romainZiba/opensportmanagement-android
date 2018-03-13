package com.zcorp.opensportmanagement.ui.eventdetails

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Player
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.content_event_detail.*
import javax.inject.Inject


class EventDetailActivity : BaseActivity(), IEventDetailView {

    @Inject
    lateinit var dataManager: IDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        super.mActivityComponent.inject(this)
        setSupportActionBar(toolbar_event_details)
        event_detail_subscription_fab.setOnClickListener { view ->
            ThemedSnackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        dataManager.getMatch(1).subscribe { match ->
            val allPlayers = mutableListOf<Player>()
            allPlayers.addAll(match.presentPlayers)
            allPlayers.addAll(match.absentPlayers)
            rv_event_participant_list.adapter = ParticipantRecyclerAdapter(allPlayers)
        }
    }
}
