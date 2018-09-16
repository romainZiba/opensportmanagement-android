package com.zcorp.opensportmanagement.ui.events

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.datasource.local.EventEntity
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDto
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.repository.NetworkState
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.events.adapter.EventsAdapter
import com.zcorp.opensportmanagement.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_event_list.event_swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_event_list.events_toolbar
import kotlinx.android.synthetic.main.fragment_event_list.rv_events_list
import kotlinx.android.synthetic.main.fragment_event_list.view.event_swipeRefreshLayout
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A fragment showing a list of Events.
 */
class EventsFragment : BaseFragment(),
        SwipeRefreshLayout.OnRefreshListener,
        EventsAdapter.OnEventClickListener {

    companion object {
        private const val TAG = "EventsFragment"
        private const val FIRST_VISIBLE_ITEM_KEY = "visibleItem"
    }

    private lateinit var mEventsAdapter: EventsAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private val viewModel: MainViewModel by sharedViewModel()
    private val mPreferencesHelper: PreferencesHelper by inject()
    private var currentTeamId = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)
        view.event_swipeRefreshLayout.setOnRefreshListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(events_toolbar as Toolbar)
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
            if (savedInstanceState != null) {
                val visibleItem = savedInstanceState.getInt(FIRST_VISIBLE_ITEM_KEY)
                mLayoutManager.scrollToPosition(visibleItem)
            }
        })
        viewModel.refreshState.observe(this, Observer { networkState: NetworkState? ->
            event_swipeRefreshLayout.isRefreshing = networkState == NetworkState.LOADING
        })
        viewModel.networkState.observe(this, Observer { networkState ->
            mEventsAdapter.setNetworkState(networkState)
        })
        viewModel.loggedState.observe(this, Observer { state ->
            when (state) {
                is State.Success -> if (this.currentTeamId != -1 && savedInstanceState == null) viewModel.getEvents(currentTeamId)
                else -> {
                }
            }
        })
        viewModel.selectedTeamId.observe(this, Observer { teamId ->
            teamId?.let {
                if (savedInstanceState == null) {
                    viewModel.getEvents(it)
                }
                currentTeamId = it
            }
        })
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

    override fun onRefresh() {
        forceRefreshData()
    }

    override fun onEventClicked(event: EventEntity, adapterPosition: Int) {
        Toast.makeText(activity, "Event clicked, id ${event._id}", Toast.LENGTH_LONG).show()
    }

    private fun forceRefreshData() {
        if (mPreferencesHelper.isLogged()) {
            viewModel.refreshEvents()
        } else {
            Toast.makeText(context, getString(R.string.error_not_logged), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(FIRST_VISIBLE_ITEM_KEY, mLayoutManager.findFirstCompletelyVisibleItemPosition())
    }
}
