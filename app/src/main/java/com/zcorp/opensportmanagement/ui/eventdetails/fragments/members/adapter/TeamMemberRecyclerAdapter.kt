package com.zcorp.opensportmanagement.ui.eventdetails.fragments.members.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.TeamMember
import com.zcorp.opensportmanagement.ui.eventdetails.IEventDetailsPresenter

/**
 * [RecyclerView.Adapter] that can display a conversation
 */
class TeamMemberRecyclerAdapter(
        private val mContext: Context,
        private val mPresenter: IEventDetailsPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                PRESENT_PLAYERS_HEADER_VIEW_TYPE -> holder.setTitle(mContext.resources.getString(R.string.present))
                ABSENT_PLAYERS_HEADER_VIEW_TYPE -> holder.setTitle(mContext.resources.getString(R.string.absent))
            }
        } else {
            val mAllPlayers = mPresenter.getTeamMembers()
            var teamMember: TeamMember = if (position <= mPresenter.getPresentMembersCount()) {
                mAllPlayers[position - 1] // There is one header to remove from calculation
            } else {
                mAllPlayers[position - 2] // There are two header to remove from calculation
            }
            (holder as TeamMemberViewHolder).setParticipantName("${teamMember.firstName} ${teamMember.lastName}")
        }
    }

    override fun getItemCount(): Int {
        return mPresenter.getTeamMembers().size + 2 // 2 header sections
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> PRESENT_PLAYERS_HEADER_VIEW_TYPE
            mPresenter.getPresentMembersCount() + 1 -> ABSENT_PLAYERS_HEADER_VIEW_TYPE
            else -> PLAYERS_VIEW_TYPE
        }
    }

    companion object {
        const val PRESENT_PLAYERS_HEADER_VIEW_TYPE = 0
        const val ABSENT_PLAYERS_HEADER_VIEW_TYPE = 1
        const val PLAYERS_VIEW_TYPE = 2
    }
}
