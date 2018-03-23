package com.zcorp.opensportmanagement.ui.main.fragments.conversations.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.Conversation
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.IConversationsPresenter

/**
 * [RecyclerView.Adapter] that can display a conversation
 */
class ConversationsAdapter(private var mConversations: List<Conversation>,
                           private val mPresenter: IConversationsPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_conversation, parent, false)
        return ConversationViewHolder(view, mPresenter)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val conversation = mConversations[position]
        (holder as IConversationViewHolder).setTopic(conversation.conversationTopic)
        holder.itemView.setOnClickListener {
            mPresenter.onConversationSelected(conversation.conversationId)
        }
    }

    override fun getItemCount(): Int {
        return mConversations.size
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    fun updateConversations(conversations: List<Conversation>) {
        mConversations = conversations
        notifyDataSetChanged()
    }
}
