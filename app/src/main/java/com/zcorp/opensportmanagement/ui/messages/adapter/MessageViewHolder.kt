package com.zcorp.opensportmanagement.ui.messages.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.zcorp.opensportmanagement.ui.messages.IMessagesPresenter
import com.zcorp.opensportmanagement.utils.datetime.DateTimeFormatter
import kotlinx.android.synthetic.main.rv_item_message_current_user.view.*
import kotlinx.android.synthetic.main.rv_item_message_friend.view.*
import org.threeten.bp.OffsetDateTime

/**
 * Created by romainz on 17/02/18.
 */
class MessageViewHolder(private val mView: View, private val mType: Int) : RecyclerView.ViewHolder(mView), IMessageViewHolder {

    override fun setMessage(message: String) {
        when (mType) {
            IMessagesPresenter.CURRENT_USER -> mView.tv_participant_name.text = message
            else -> mView.tv_friend_message_content.text = message
        }
    }

    override fun setMessageUserAndDate(user: String, dateTime: OffsetDateTime) {
        val toDisplay = user + " - " + DateTimeFormatter.dateFormatterForMessages.format(dateTime)
        when (mType) {
            IMessagesPresenter.CURRENT_USER -> mView.tv_user_message_username_and_date.text = toDisplay
            else -> mView.tv_friend_message_username_and_date.text = toDisplay
        }
    }

    override fun getType(): Int {
        return mType
    }
}