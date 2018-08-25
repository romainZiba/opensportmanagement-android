package com.zcorp.opensportmanagement.ui.messages

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.InAppMessage
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsFragment.Companion.CONVERSATION_ID_KEY
import com.zcorp.opensportmanagement.ui.messages.adapter.MessagesAdapter
import kotlinx.android.synthetic.main.activity_messages.*
import javax.inject.Inject

/**
 * A fragment with messages
 */
class MessagesActivity : BaseActivity(), IMessagesView {

    @Inject
    lateinit var presenter: IMessagesPresenter

    @Inject
    lateinit var mAdapter: MessagesAdapter

    var mSnackbar: Snackbar? = null

    override fun showNetworkError() {
        Log.d("", "we should show a network error")
    }

    override fun onMessagesAvailable(messages: List<InAppMessage>) {
        mAdapter.updateMessages(messages)
    }

    override fun displayNewMessage(message: InAppMessage) {
        mAdapter.addMessage(message)
    }

    override fun isAtBottom(): Boolean {
        return !rv_messages_list.canScrollVertically(DIRECTION_SCROLL_DOWN)
    }

    override fun moveToEnd() {
        rv_messages_list.scrollToPosition(mAdapter.itemCount - 1)
    }

    override fun clearInputMessage() {
        et_message.text.clear()
    }

    override fun showNewMessageIndicator() {
        mSnackbar = ThemedSnackbar.make(findViewById<View>(android.R.id.content).rootView,
                "New message available", Snackbar.LENGTH_LONG)
                .setAction("OK", {
                    mSnackbar!!.dismiss()
                    mSnackbar = null
                })
        mSnackbar!!.show()
    }

    override fun showProgress() {
        TODO("not implemented") // To change message of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)
        setSupportActionBar(messages_toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.mActivityComponent.inject(this)
        val conversationId = intent.getStringExtra(CONVERSATION_ID_KEY)
        presenter.setConversationId(conversationId)
        rv_messages_list.adapter = mAdapter
        btn_send_message.setOnClickListener { presenter.postMessage(et_message.text.toString()) }
        val layoutManager = rv_messages_list.layoutManager as LinearLayoutManager
        layoutManager.stackFromEnd = true
        presenter.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}
