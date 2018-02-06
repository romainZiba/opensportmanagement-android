package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.api.EventApiImpl
import kotlinx.android.synthetic.main.fragment_event_list.*

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class EventFragment : Fragment(), EventsView {

    private var mColumnCount = 1
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var eventsNetworkError: TextView

    private var presenter: EventsPresenter? = null
    private var fabExpanded: Boolean = true

    private lateinit var fabEvent: LinearLayout
    private lateinit var fabMatch: LinearLayout
    private lateinit var fabDefaultEvent: LinearLayout
    private lateinit var fabAddEvent: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
        if (presenter == null) {
            presenter = EventsPresenterImpl(this, EventsModelImpl(EventApiImpl()))
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val coordinatorLayout = inflater!!.inflate(R.layout.fragment_event_list, container, false) as CoordinatorLayout
        fabEvent = coordinatorLayout.findViewById(R.id.layoutFabAddEvent)
        fabMatch = coordinatorLayout.findViewById(R.id.layoutFabAddMatch)
        fabDefaultEvent = coordinatorLayout.findViewById(R.id.layoutFabAddDefaultEvent)
        fabAddEvent = coordinatorLayout.findViewById(R.id.fabAddEvent)
        swipeRefreshLayout = coordinatorLayout.findViewById(R.id.swipeRefreshLayout)
        recyclerView = swipeRefreshLayout.findViewById(R.id.list)
        eventsNetworkError = coordinatorLayout.findViewById(R.id.eventsNetworkError)
        // Set the adapter
        recyclerView.adapter = EventRecyclerAdapter(presenter!!)
        closeSubMenusFab()
        fabEvent.setOnClickListener {
            if (fabExpanded) {
                closeSubMenusFab()
            } else {
                openSubMenusFab()
            }
        }
        return coordinatorLayout
    }

    override fun onResume() {
        super.onResume()
        swipeRefreshLayout.setOnRefreshListener { presenter!!.getEvents() }
        presenter!!.getEvents()
    }

    override fun showNetworkError() {
        eventsNetworkError.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onDataAvailable() {
        recyclerView.visibility = View.VISIBLE
        eventsNetworkError.visibility = View.INVISIBLE
        recyclerView.adapter.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }

    //closes FAB submenus
    private fun closeSubMenusFab() {
        fabMatch.visibility = View.INVISIBLE
        fabDefaultEvent.visibility = View.INVISIBLE
        fabAddEvent.setImageResource(R.drawable.ic_add_black_24dp)
        fabExpanded = false
    }

    //Opens FAB submenus
    private fun openSubMenusFab() {
        fabMatch.visibility = View.VISIBLE
        fabDefaultEvent.visibility = View.VISIBLE
        fabAddEvent.setImageResource(R.drawable.ic_clear_black_24dp)
        fabExpanded = true
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): EventFragment {
            val fragment = EventFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
