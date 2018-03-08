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
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsFragment.Companion.CONVERSATION_ID_KEY
import com.zcorp.opensportmanagement.ui.messages.adapter.MessageRecyclerAdapter
import kotlinx.android.synthetic.main.activity_messages.*
import javax.inject.Inject

/**
 * A fragment with messages
 */
class MessagesActivity : BaseActivity(), IMessagesView {

    @Inject
    lateinit var presenter: IMessagesPresenter

    var mSnackbar: Snackbar? = null

    override fun showNetworkError() {
        Log.d("", "we should show a network error")
    }

    override fun onMessagesAvailable() {
        rv_messages_list.adapter.notifyDataSetChanged()
    }

    override fun scrollToPosition(position: Int) {
        rv_messages_list.scrollToPosition(position)
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
                "New message available", Snackbar.LENGTH_LONG)
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
        super.mActivityComponent.inject(this)
        val conversationId = intent.getStringExtra(CONVERSATION_ID_KEY)
        presenter.setConversationId(conversationId)
        rv_messages_list.adapter = MessageRecyclerAdapter(presenter)
        btn_send_message.setOnClickListener { presenter.onPostMessage(et_message.text.toString()) }
        val layoutManager = rv_messages_list.layoutManager as LinearLayoutManager
        layoutManager.stackFromEnd = true
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onDetach()
    }
}
