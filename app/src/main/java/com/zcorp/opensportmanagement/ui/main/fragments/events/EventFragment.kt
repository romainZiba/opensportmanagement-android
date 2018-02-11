package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.app.Fragment
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.di.component.DaggerFragmentComponent
import com.zcorp.opensportmanagement.di.module.FragmentModule
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
class EventFragment : Fragment(), IEventsView {

    private var mColumnCount = 1

    @Inject
    lateinit var presenter: IEventsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFragmentComponent.builder()
                .appComponent(MyApplication.appComponent)
                .fragmentModule(FragmentModule(this))
                .build()
                .inject(this)
        presenter.onAttach(this)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_event_list, container, false)
        view.list.adapter = EventRecyclerAdapter(presenter)
        view.menu.setOnMenuButtonClickListener({
            presenter.onFloatingMenuClicked()
        })
        return view
    }

    override fun onResume() {
        super.onResume()
        swipeRefreshLayout.setOnRefreshListener { presenter.getEventsFromModel() }
        presenter.getEventsFromModel()
    }

    override fun showNetworkError() {
        eventsNetworkError.visibility = View.VISIBLE
        list.visibility = View.INVISIBLE
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onDataAvailable() {
        list.visibility = View.VISIBLE
        eventsNetworkError.visibility = View.INVISIBLE
        list.adapter.notifyDataSetChanged()
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

    fun setPresenterForTest(p: IEventsPresenter) {
        presenter = p
    }
}
