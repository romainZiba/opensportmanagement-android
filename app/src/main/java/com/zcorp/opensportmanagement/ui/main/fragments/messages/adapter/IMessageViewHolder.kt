package com.zcorp.opensportmanagement.ui.main.fragments.messages.adapter

/**
 * Created by romainz on 17/02/18.
 */
interface IMessageViewHolder {
    fun setMessage(message: String)
    fun getType(): Int
}