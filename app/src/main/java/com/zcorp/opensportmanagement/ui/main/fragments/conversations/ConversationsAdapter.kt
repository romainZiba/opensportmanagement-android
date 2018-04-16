package com.zcorp.opensportmanagement.ui.main.fragments.conversations

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.Conversation
import kotlinx.android.synthetic.main.rv_item_conversation.view.*

/**
 * [RecyclerView.Adapter] that can display conversations
 */
class ConversationsAdapter(
        private val callback: OnConversationListener,
        private var mConversations: List<Conversation>) : RecyclerView.Adapter<ConversationsAdapter.ConversationViewHolder>() {

    interface OnConversationListener {
        fun onConversationSelected(conversation: Conversation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_conversation, parent, false)
        return ConversationViewHolder(callback, view)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bindView(mConversations[position])
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

    class ConversationViewHolder(private val callback: OnConversationListener,
                                 private val mView: View) : RecyclerView.ViewHolder(mView) {
        fun bindView(conversation: Conversation) {
            mView.tv_conversation_topic.text = conversation.conversationTopic
            mView.setOnClickListener {
                callback.onConversationSelected(conversation)
            }
        }
    }
}
