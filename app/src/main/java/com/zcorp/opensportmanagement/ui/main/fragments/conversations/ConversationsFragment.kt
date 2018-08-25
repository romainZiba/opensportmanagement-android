package com.zcorp.opensportmanagement.ui.main.fragments.conversations

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.Conversation
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseFragment
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
class ConversationsFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, ConversationsAdapter.OnConversationListener {

    companion object {
        const val CONVERSATION_ID_KEY = "conversationId"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mAdapter: ConversationsAdapter
    private lateinit var viewModel: ConversationViewModel

    fun showNetworkError() {
        ThemedSnackbar.make(view!!, R.string.network_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), { viewModel.getConversations() })
                .show()
    }

    fun showProgress() {
        conversations_swipeRefreshLayout.isRefreshing = true
    }

    fun hideProgress() {
        conversations_swipeRefreshLayout.isRefreshing = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_conversation_list, container, false)
        val recyclerView = view.rv_conversations_list
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        mAdapter = ConversationsAdapter(this, emptyList())
        recyclerView.adapter = mAdapter
        view.conversations_swipeRefreshLayout.setOnRefreshListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory)
                .get(ConversationViewModel::class.java)
        viewModel.states.observe(this, Observer {
            when (it) {
                is State.Success -> mAdapter.updateConversations(it.data)
                is State.Progress -> {
                    if (it.loading) showProgress()
                    else hideProgress()
                }
                is State.Failure -> showNetworkError()
            }
        })
        viewModel.getConversations()
    }

    override fun onRefresh() {
        viewModel.getConversations()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.refresh_events -> {
                viewModel.getConversations()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onConversationSelected(conversation: Conversation) {
        val intent = Intent(mActivity, MessagesActivity::class.java)
        intent.putExtra(CONVERSATION_ID_KEY, conversation.conversationId)
        startActivity(intent)
    }
}
