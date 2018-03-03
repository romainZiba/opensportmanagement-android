package com.zcorp.opensportmanagement.ui.main.fragments.conversations.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.IConversationsPresenter

/**
 * [RecyclerView.Adapter] that can display a conversation
 */
class ConversationsRecyclerAdapter(private val presenter: IConversationsPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_conversation, parent, false)
        return ConversationViewHolder(view, presenter)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        presenter.onBindConversationRowViewAtPosition(position, holder as IConversationViewHolder)
    }

    override fun getItemCount(): Int {
        return presenter.getConversationsCount()
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}
