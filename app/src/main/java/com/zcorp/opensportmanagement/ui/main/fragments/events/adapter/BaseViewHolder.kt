package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.event_participant_number.view.*

/**
 * Created by romainz on 12/02/18.
 */
abstract class BaseViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {

    abstract fun setDate(dateAsString: String)

    fun setParticipantsNumber(number: Int) {
        mView.tv_participants_number.text = number.toString()
    }

    abstract fun setPlace(place: String)
}