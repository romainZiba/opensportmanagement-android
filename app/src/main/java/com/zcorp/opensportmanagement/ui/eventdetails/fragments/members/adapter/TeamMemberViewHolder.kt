package com.zcorp.opensportmanagement.ui.eventdetails.fragments.members.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.rv_item_participant.view.*

/**
 * Created by romainz on 12/02/18.
 */
class TeamMemberViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {

    fun setParticipantName(name: String) {
        mView.tv_participant_name.text = name
    }

    fun setListener() {
        mView.setOnClickListener({
        })
    }
}