package com.zcorp.opensportmanagement.ui.main.fragments.messages.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.zcorp.opensportmanagement.ui.main.fragments.messages.IMessagesPresenter
import kotlinx.android.synthetic.main.rv_item_message_current_user.view.*
import kotlinx.android.synthetic.main.rv_item_message_friend.view.*

/**
 * Created by romainz on 17/02/18.
 */
class MessageViewHolder(private val mView: View, private val mType: Int) : RecyclerView.ViewHolder(mView), IMessageViewHolder {

    override fun setMessage(message: String) {
        when (mType) {
            IMessagesPresenter.CURRENT_USER -> mView.tv_user_message_content.text = message
            else -> mView.tv_message_friend.text = message
        }
    }

    override fun getType(): Int {
        return mType
    }
}