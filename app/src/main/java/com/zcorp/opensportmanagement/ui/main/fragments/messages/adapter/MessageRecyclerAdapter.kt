package com.zcorp.opensportmanagement.ui.main.fragments.messages.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.main.fragments.messages.IMessagesPresenter

/**
 * [RecyclerView.Adapter] that can display a [Event]
 */
class MessageRecyclerAdapter(private val presenter: IMessagesPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            IMessagesPresenter.CURRENT_USER -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_message_current_user, parent, false)
                MessageViewHolder(view, IMessagesPresenter.CURRENT_USER)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_message_friend, parent, false)
                MessageViewHolder(view, IMessagesPresenter.FRIEND)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        presenter.onBindMessageRowViewAtPosition(position, holder as IMessageViewHolder)
    }

    override fun getItemCount(): Int {
        return presenter.getMessagesCount()
    }

    override fun getItemViewType(position: Int): Int {
        return presenter.getMessageType(position)
    }
}
