package com.zcorp.opensportmanagement.ui.main.fragments.messages

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.ThemedSnackbar
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.main.fragments.messages.adapter.MessageRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.fragment_messages.view.*
import javax.inject.Inject

/**
 * A fragment with messages
 */
class MessagesFragment : BaseFragment(), IMessagesView {

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
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        et_message.text.clear()
    }

    override fun showNewMessageIndicator() {
        mSnackbar = ThemedSnackbar.make(this.view, "New message available", Snackbar.LENGTH_LONG)
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
        super.mFragmentComponent.inject(this)
        presenter.onAttach(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_messages, container, false)
        view.rv_messages_list.adapter = MessageRecyclerAdapter(presenter)
        view.btn_send_message.setOnClickListener { presenter.onPostMessage(view.et_message.text.toString()) }
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.getMessagesFromApi()
    }
}
