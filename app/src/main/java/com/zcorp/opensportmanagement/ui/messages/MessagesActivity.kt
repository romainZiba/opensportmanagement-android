package com.zcorp.opensportmanagement.ui.messages

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    override fun moveToEnd() {
        rv_messages_list.scrollToPosition(mAdapter.itemCount - 1)
    }

    override fun closeKeyboardAndClear() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        et_message.text.clear()
    }

    override fun showNewMessageIndicator() {
        mSnackbar = ThemedSnackbar.make(findViewById<View>(android.R.id.content).rootView,
                "New body available", Snackbar.LENGTH_LONG)
                .setAction("OK", {
                    mSnackbar!!.dismiss()
                    mSnackbar = null
                })
        mSnackbar!!.show()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
