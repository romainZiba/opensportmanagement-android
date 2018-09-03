package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.datasource.local.EventEntity
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDto
import com.zcorp.opensportmanagement.repository.NetworkState
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.main.MainViewModel
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.EventsAdapter
import kotlinx.android.synthetic.main.fragment_event_list.event_swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_event_list.events_background_layout
import kotlinx.android.synthetic.main.fragment_event_list.menu_events
import kotlinx.android.synthetic.main.fragment_event_list.rv_events_list
import kotlinx.android.synthetic.main.fragment_event_list.view.event_swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_event_list.view.fab_add_event
import kotlinx.android.synthetic.main.fragment_event_list.view.menu_events
import org.koin.android.architecture.ext.viewModel

/**
 * A fragment showing a list of Events.
 */
class EventsFragment : BaseFragment(),
        SwipeRefreshLayout.OnRefreshListener,
        EventsAdapter.OnEventClickListener {

    private lateinit var mEventsAdapter: EventsAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private val viewModel: EventsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)
        view.menu_events.setOnMenuButtonClickListener {
            if (view.menu_events.isOpened) {
                closeFloatingMenu()
            } else {
                openFloatingMenu()
            }
        }
        view.fab_add_event.setOnClickListener {
            closeFloatingMenu()
            // TODO: event creation
//            startActivity(Intent(activity, EventCreationActivity::class.java))
        }
        view.event_swipeRefreshLayout.setOnRefreshListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mEventsAdapter = EventsAdapter()
        mLayoutManager = LinearLayoutManager(activity)

        rv_events_list.apply {
            adapter = mEventsAdapter
            layoutManager = mLayoutManager
            val dividerItemDecoration = DividerItemDecoration(this.context, mLayoutManager.orientation)
            this.addItemDecoration(dividerItemDecoration)
        }

        viewModel.eventsLiveData.observe(this, Observer<PagedList<EventDto>> {
            mEventsAdapter.submitList(it)
        })
        viewModel.refreshState.observe(this, Observer { networkState: NetworkState? ->
            event_swipeRefreshLayout.isRefreshing = networkState == NetworkState.LOADING
        })
        viewModel.networkState.observe(this, Observer { networkState ->
            mEventsAdapter.setNetworkState(networkState)
        })

        viewModel.getEvents()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.refresh_events -> {
                forceRefreshData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onTeamSelected() {
        forceRefreshData()
    }

    override fun onRefresh() {
        forceRefreshData()
    }

    override fun onEventClicked(event: EventEntity, adapterPosition: Int) {
        if (menu_events.isOpened) {
            closeFloatingMenu()
            return
        }
        // TODO: event details
        Toast.makeText(activity, "Event clicked, id ${event._id}", Toast.LENGTH_LONG).show()
//        val intent = Intent(mActivity, EventDetailsActivity::class.java)
//        intent.putExtra("event", event)
//        startActivity(intent)
    }

    private fun openFloatingMenu() {
        menu_events.open(true)
        setBackgroundAlpha(0.3f)
    }

    private fun closeFloatingMenu() {
        menu_events.close(true)
        setBackgroundAlpha(1.0f)
    }

    private fun setBackgroundAlpha(alpha: Float) {
        events_background_layout.alpha = alpha
    }

    private fun forceRefreshData() {
        viewModel.refresh()
    }
}
