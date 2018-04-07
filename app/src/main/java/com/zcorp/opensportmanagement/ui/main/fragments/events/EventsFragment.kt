package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.repository.Resource
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.eventcreation.EventCreationActivity
import com.zcorp.opensportmanagement.ui.eventdetails.EventDetailsActivity
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.EventsAdapter
import com.zcorp.opensportmanagement.utils.log.ILogger
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.android.synthetic.main.fragment_event_list.view.*
import javax.inject.Inject


/**
 * A fragment showing a list of Events.
 */
class EventsFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, EventsAdapter.OnEventClickListener {

    companion object {
        private val TAG = EventsFragment::class.java.simpleName
    }

    @Inject lateinit var mLogger: ILogger

    lateinit var mEventsAdapter: EventsAdapter
    private lateinit var mEventsViewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mEventsViewModel = ViewModelProviders.of(activity!!).get(EventsViewModel::class.java)

        mEventsAdapter = EventsAdapter(this, mutableListOf())
        rv_events_list.adapter = mEventsAdapter

        mEventsViewModel.getEvents().observe(this, Observer {
            when (it) {
                is Resource.Failure -> {
                    mLogger.d(TAG, "Error occurred ${it.e}")
                    showNetworkError()
                }
                is Resource.Progress -> {
                    if (it.loading) showProgress()
                    else hideProgress()
                }
                is Resource.Success -> {
                    mLogger.d(TAG, "Events retrieved ${it.data}")
                    mEventsAdapter.updateEvents(it.data)
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)
        view.menu_events.setOnMenuButtonClickListener({
            if (view.menu_events.isOpened) {
                closeFloatingMenu()
            } else {
                openFloatingMenu()
            }
        })
        view.fab_add_event.setOnClickListener {
            closeFloatingMenu()
            startActivity(Intent(activity, EventCreationActivity::class.java))
        }
        view.event_swipeRefreshLayout.setOnRefreshListener(this)
        return view
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

    private fun showNetworkError() {
        ThemedSnackbar.make(view!!, R.string.network_error, Snackbar.LENGTH_LONG).show()
    }

    override fun onEventClicked(event: Event, adapterPosition: Int) {
        if (menu_events.isOpened) {
            closeFloatingMenu()
            return
        }
        val intent = Intent(mActivity, EventDetailsActivity::class.java)
        intent.putExtra("event", event)
        startActivity(intent)
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

    private fun showProgress() {
        event_swipeRefreshLayout.isRefreshing = true
    }

    private fun hideProgress() {
        event_swipeRefreshLayout.isRefreshing = false
    }

    private fun forceRefreshData() {
        mEventsViewModel.forceRefreshEvents()
    }

}
