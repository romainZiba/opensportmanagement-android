package com.zcorp.opensportmanagement.ui.eventdetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.Player

/**
 * [RecyclerView.Adapter] that can display a conversation
 */
class ParticipantRecyclerAdapter(private val mParticipants: MutableList<Player>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_participant, parent, false)
        return ParticipantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ParticipantViewHolder).setParticipantName("${mParticipants[position].firstName} ${mParticipants[position].lastName}")
    }

    override fun getItemCount(): Int {
        return mParticipants.size
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}
