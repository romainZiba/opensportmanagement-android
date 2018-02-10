package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.github.clans.fab.FloatingActionMenu
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.api.EventApiImpl
import com.zcorp.opensportmanagement.application.MyApplication
import com.zcorp.opensportmanagement.screens.dagger.DaggerMainComponent
import com.zcorp.opensportmanagement.screens.dagger.MainModule
import com.zcorp.opensportmanagement.screens.login.dagger.DaggerLoginComponent
import com.zcorp.opensportmanagement.screens.login.dagger.LoginContextModule
import javax.inject.Inject

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
    private lateinit var menu: FloatingActionMenu

    @Inject
    lateinit var presenter: EventsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerMainComponent.builder()
                .appComponent(MyApplication.appComponent)
                .mainModule(MainModule(this))
                .build()
                .inject(this)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val coordinatorLayout = inflater!!.inflate(R.layout.fragment_event_list, container, false) as CoordinatorLayout

        menu = coordinatorLayout.findViewById(R.id.menu)
        swipeRefreshLayout = coordinatorLayout.findViewById(R.id.swipeRefreshLayout)
        recyclerView = swipeRefreshLayout.findViewById(R.id.list)
        eventsNetworkError = coordinatorLayout.findViewById(R.id.eventsNetworkError)
        // Set the adapter
        recyclerView.adapter = EventRecyclerAdapter(presenter)
        return coordinatorLayout
    }

    override fun onResume() {
        super.onResume()
        swipeRefreshLayout.setOnRefreshListener { presenter.getEvents() }
        presenter.getEvents()
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

    override fun showRowClicked(s: String) {
        Toast.makeText(this.activity, s, Toast.LENGTH_LONG).show()
    }

    override fun isFabButtonOpened(): Boolean {
        return menu.isOpened
    }

    override fun closeFabButton() {
        menu.close(true)
    }

    fun setPresenterForTest(p: EventsPresenter) {
        presenter = p
    }
}
