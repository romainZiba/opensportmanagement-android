package com.zcorp.opensportmanagement.ui.main.fragments.messages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zcorp.opensportmanagement.R
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

    override fun showNetworkError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMessagesAvailable() {
        rv_messages_list.adapter.notifyDataSetChanged()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendMessage(message: String) {
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
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.getMessagesFromApi()
        Toast.makeText(activity.baseContext, "Current user is " + presenter.getCurrentUserName(), Toast.LENGTH_LONG).show()
    }

}
