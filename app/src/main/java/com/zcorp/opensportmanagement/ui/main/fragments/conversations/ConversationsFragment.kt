package com.zcorp.opensportmanagement.ui.main.fragments.conversations

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.adapter.ConversationsRecyclerAdapter
import com.zcorp.opensportmanagement.ui.messages.MessagesActivity
import kotlinx.android.synthetic.main.fragment_conversation_list.*
import kotlinx.android.synthetic.main.fragment_conversation_list.view.*
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
class ConversationsFragment : BaseFragment(), IConversationsView, SwipeRefreshLayout.OnRefreshListener {

    private var mColumnCount = 1

    @Inject
    lateinit var presenter: IConversationsPresenter

    override fun showNetworkError() {
        ThemedSnackbar.make(view, R.string.network_error, Snackbar.LENGTH_LONG).show()
        conversations_swipeRefreshLayout.isRefreshing = false
    }

    override fun onDataAvailable() {
        rv_conversations_list.adapter.notifyDataSetChanged()
        conversations_swipeRefreshLayout.isRefreshing = false
    }

    override fun showConversationDetails(conversationId: String) {
        val intent = Intent(activity, MessagesActivity::class.java)
        intent.putExtra(CONVERSATION_ID_KEY, conversationId)
        startActivity(intent)
    }

    override fun showProgress() {
        conversations_swipeRefreshLayout.isRefreshing = true
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
        val view = inflater!!.inflate(R.layout.fragment_conversation_list, container, false)
        val recyclerView = view.rv_conversations_list
        recyclerView.adapter = ConversationsRecyclerAdapter(presenter)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        conversations_swipeRefreshLayout.setOnRefreshListener(this)
        presenter.getConversationsFromModel()
    }

//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater!!.inflate(R.menu.events_toolbar, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return when (item?.itemId) {
//            R.id.refresh_events -> {
//                presenter.getConversationsFromModel()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onRefresh() {
        presenter.getConversationsFromModel()
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        public val CONVERSATION_ID_KEY = "conversationId"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): ConversationsFragment {
            val fragment = ConversationsFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
