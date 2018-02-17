package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

/**
 * Created by romainz on 03/02/18.
 */
interface IEventViewHolder {
    fun setListener()
    fun setDate(dateAsString: String)
    fun setParticipantsNumber(number: Int)
    fun setDescription(description: String)
}