package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.eventdetails.EventDetailActivity
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.EventRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.android.synthetic.main.fragment_event_list.view.*
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
class EventFragment : BaseFragment(), IEventsView, SwipeRefreshLayout.OnRefreshListener {

    private var mColumnCount = 1

    @Inject
    lateinit var presenter: IEventsPresenter

    override fun showNetworkError() {
        eventsNetworkError.visibility = View.VISIBLE
        rv_events_list.visibility = View.INVISIBLE
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onDataAvailable() {
        rv_events_list.visibility = View.VISIBLE
        eventsNetworkError.visibility = View.INVISIBLE
        rv_events_list.adapter.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showEventDetails(eventId: Int) {
        startActivity(Intent(activity, EventDetailActivity::class.java))
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
        events_background_layout.background = ContextCompat.getDrawable(activity.baseContext, drawableId)
    }

    override fun showProgress() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
        presenter.onAttach(this)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_event_list, container, false)
        view.rv_events_list.adapter = EventRecyclerAdapter(presenter)
        view.menu.setOnMenuButtonClickListener({
            presenter.onFloatingMenuClicked()
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        swipeRefreshLayout.setOnRefreshListener(this)
        presenter.getEventsFromModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.events_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.refresh_events -> {
                presenter.getEventsFromModel()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onRefresh() {
        presenter.getEventsFromModel()
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

    fun setPresenterForTest(p: IEventsPresenter) {
        presenter = p
    }
}
