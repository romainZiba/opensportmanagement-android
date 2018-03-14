package com.zcorp.opensportmanagement.ui.eventdetails

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.Match
import com.zcorp.opensportmanagement.ui.DividerDecoration
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

        dataManager.getMatch(1).subscribe { match ->
            rv_event_participant_list.adapter = ParticipantRecyclerAdapter(
                    this, match.presentPlayers.toMutableList(), match.absentPlayers.toMutableList())
        }

        val dividerItemDecoration = DividerDecoration(rv_event_participant_list.context)
        rv_event_participant_list.addItemDecoration(dividerItemDecoration)

        switch_presence_event_detail.onChangeListener = object : ToggleSwitch.OnChangeListener {
            override fun onToggleSwitchChanged(position: Int) {
                when (position) {
                    0 -> {
                        switch_presence_event_detail.checkedBackgroundColor = ContextCompat.getColor(
                                this@EventDetailActivity, R.color.green_500)
                        switch_presence_event_detail.reDraw()
                    }
                    1 -> {
                        switch_presence_event_detail.checkedBackgroundColor = ContextCompat.getColor(
                                this@EventDetailActivity, R.color.red_900)
                        switch_presence_event_detail.reDraw()
                    }
                }
            }

        }
    }
}
