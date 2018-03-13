package com.zcorp.opensportmanagement.ui.main.fragments.conversations

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
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

    @Inject
    lateinit var presenter: IConversationsPresenter

    override fun showNetworkError() {
        ThemedSnackbar.make(view!!, R.string.network_error, Snackbar.LENGTH_LONG).show()
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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_conversation_list, container, false)
        val recyclerView = view.rv_conversations_list
        recyclerView.adapter = ConversationsRecyclerAdapter(presenter)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        view.conversations_swipeRefreshLayout.setOnRefreshListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        presenter.getConversationsFromModel()
    }

    override fun onRefresh() {
        presenter.getConversationsFromModel()
    }

    companion object {
        val CONVERSATION_ID_KEY = "conversationId"
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.messages_menu_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.refresh_conversations -> {
                presenter.getConversationsFromModel()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
