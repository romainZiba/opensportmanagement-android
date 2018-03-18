package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.eventdetails.EventDetailsActivity
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.EventRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.android.synthetic.main.fragment_event_list.view.*
import javax.inject.Inject

/**
 * A fragment representing a list of Events.
 */
class EventsFragment : BaseFragment(), IEventsView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var presenter: IEventsPresenter

    @Inject
    lateinit var fragmentContext: Context

    @Inject
    lateinit var mActivity: Activity

    override fun showNetworkError() {
        ThemedSnackbar.make(view!!, R.string.network_error, Snackbar.LENGTH_LONG).show()
        event_swipeRefreshLayout.isRefreshing = false
    }

    override fun onDataAvailable() {
        rv_events_list.adapter.notifyDataSetChanged()
        event_swipeRefreshLayout.isRefreshing = false
    }

    override fun showEventDetails(event: Event, adapterPosition: Int) {
        // get the common element for the transition in this activity
        val viewToAnimate = rv_events_list.layoutManager.findViewByPosition(adapterPosition)
        val intent = Intent(activity, EventDetailsActivity::class.java)
        intent.putExtra("event", event)
        if (viewToAnimate != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity
//                    viewToAnimate,
//                    context!!.getString(R.string.transition_match_description)
            )
                    .toBundle()
            startActivity(intent, bundle)
        } else {
            startActivity(intent)
        }
    }

    override fun isFloatingMenuOpened(): Boolean {
        return menu.isOpened
    }

    override fun openFloatingMenu() {
        menu.open(true)
    }

    override fun closeFloatingMenu() {
        menu.close(true)
    }

    override fun setBackgroundAlpha(alpha: Float) {
        events_background_layout.alpha = alpha
    }

    override fun setBackgroundColor(colorResourceId: Int) {
        events_background_layout.setBackgroundColor(colorResourceId)
    }

    override fun setBackground(drawableId: Int) {
        events_background_layout.background = ContextCompat.getDrawable(fragmentContext, drawableId)
    }

    override fun showProgress() {
        event_swipeRefreshLayout.isRefreshing = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
        presenter.onAttach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)
        view.rv_events_list.adapter = EventRecyclerAdapter(presenter)
        view.menu.setOnMenuButtonClickListener({
            presenter.onFloatingMenuClicked()
        })
        view.event_swipeRefreshLayout.setOnRefreshListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        presenter.getEventsFromModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.events_menu_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.refresh_events -> {
                presenter.getEventsFromModel()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRefresh() {
        presenter.getEventsFromModel()
    }
}
