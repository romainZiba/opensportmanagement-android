package com.zcorp.opensportmanagement.ui.messages.adapter

import org.threeten.bp.OffsetDateTime

/**
 * Created by romainz on 17/02/18.
 */
interface IMessageViewHolder {
    fun setMessage(message: String)
    fun setMessageUserAndDate(user: String, dateTime: OffsetDateTime)
    fun getType(): Int
}