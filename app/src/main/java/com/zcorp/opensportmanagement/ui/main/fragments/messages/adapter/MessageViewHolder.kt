package com.zcorp.opensportmanagement.ui.main.fragments.messages.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.rv_item_message_friend.view.*

/**
 * Created by romainz on 17/02/18.
 */
class MessageViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView), IMessageViewHolder {

    override fun setMessage(message: String) {
        mView.tv_message_friend.text = message
    }

}