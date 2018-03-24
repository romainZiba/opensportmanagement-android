package com.zcorp.opensportmanagement.ui.messages.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.InAppMessage
import com.zcorp.opensportmanagement.ui.messages.IMessagesPresenter

/**
 * [RecyclerView.Adapter] that can display a [Event]
 */
class MessagesAdapter(
        private var mMessages: MutableList<InAppMessage>,
        private val presenter: IMessagesPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        val inAppMessage = mMessages[position]
        (holder as IMessageViewHolder).setMessage(inAppMessage.message)
        holder.setMessageUserAndDate(inAppMessage.from, inAppMessage.time)
    }

    override fun getItemCount(): Int {
        return mMessages.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (mMessages[position].from) {
            presenter.getCurrentUserName() -> IMessagesPresenter.CURRENT_USER
            else -> IMessagesPresenter.FRIEND
        }
    }

    fun updateMessages(messages: List<InAppMessage>) {
        mMessages = messages.toMutableList()
        notifyDataSetChanged()
    }

    fun addMessage(message: InAppMessage) {
        mMessages.add(message)
        notifyDataSetChanged()
    }
}
