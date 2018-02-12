package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

/**
 * Created by romainz on 11/02/18.
 */
interface IViewHolder {
    fun setListener()
    fun setDate(dateAsString: String)
    fun setParticipantsNumber(number: Int)
}