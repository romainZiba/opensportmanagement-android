package com.zcorp.opensportmanagement.ui.eventdetails.fragments

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.TeamMember

/**
 * [RecyclerView.Adapter] that can display a conversation
 */
class TeamMemberRecyclerAdapter(private val context: Context,
                                private val mPresentTeamMembers: MutableList<TeamMember>,
                                private val mAbsentTeamMembers: MutableList<TeamMember>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val mAllPlayers = mutableListOf<TeamMember>()

    init {
        mAllPlayers.addAll(mPresentTeamMembers)
        mAllPlayers.addAll(mAbsentTeamMembers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PRESENT_PLAYERS_HEADER_VIEW_TYPE, ABSENT_PLAYERS_HEADER_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_participant_header, parent, false)
                HeaderViewHolder(view, viewType)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_participant, parent, false)
                TeamMemberViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            when (holder.viewType) {
                PRESENT_PLAYERS_HEADER_VIEW_TYPE -> holder.setTitle(context.resources.getString(R.string.present))
                ABSENT_PLAYERS_HEADER_VIEW_TYPE -> holder.setTitle(context.resources.getString(R.string.absent))
            }
        } else {
            var teamMember: TeamMember = if (position <= mPresentTeamMembers.size) {
                mAllPlayers[position - 1] // There is one header to remove from calculation
            } else {
                mAllPlayers[position - 2] // There are two header to remove from calculation
            }
            (holder as TeamMemberViewHolder).setParticipantName("${teamMember.firstName} ${teamMember.lastName}")
        }
    }

    override fun getItemCount(): Int {
        return mAllPlayers.size + 2 // 2 header sections
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> PRESENT_PLAYERS_HEADER_VIEW_TYPE
            mPresentTeamMembers.size + 1 -> ABSENT_PLAYERS_HEADER_VIEW_TYPE
            else -> PLAYERS_VIEW_TYPE
        }
    }

    companion object {
        const val PRESENT_PLAYERS_HEADER_VIEW_TYPE = 0
        const val ABSENT_PLAYERS_HEADER_VIEW_TYPE = 1
        const val PLAYERS_VIEW_TYPE = 2
    }
}
