package com.zcorp.opensportmanagement.ui.main.fragments.conversations.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.IConversationsPresenter
import kotlinx.android.synthetic.main.rv_item_conversation.view.*

/**
 * Created by romainz on 12/02/18.
 */
class ConversationViewHolder(private val mView: View,
                                      private val presenter: IConversationsPresenter) : RecyclerView.ViewHolder(mView), IConversationViewHolder {
    override fun setTopic(topic: String) {
        mView.tv_conversation_topic.text = topic
    }

    override fun setListener() {
        mView.setOnClickListener({
            presenter.onItemClicked(adapterPosition)
        })
    }
}