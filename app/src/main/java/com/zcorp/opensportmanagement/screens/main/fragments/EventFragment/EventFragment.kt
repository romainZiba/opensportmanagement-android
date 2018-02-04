package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import android.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.api.EventApiImpl

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

    private var presenter: EventsPresenter? = null

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
        swipeRefreshLayout = inflater!!.inflate(R.layout.fragment_event_list, container, false) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener { presenter!!.getEvents() }
        presenter!!.getEvents()
        recyclerView = swipeRefreshLayout.findViewById(R.id.list)
        // Set the adapter
        recyclerView.adapter = EventRecyclerAdapter(presenter!!)
        return swipeRefreshLayout
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable("presenter", presenter)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            presenter = savedInstanceState.getSerializable("presenter") as EventsPresenter
        }
        super.onViewStateRestored(savedInstanceState)
    }

    override fun showNetworkError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDataAvailable() {
        recyclerView.adapter.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
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
