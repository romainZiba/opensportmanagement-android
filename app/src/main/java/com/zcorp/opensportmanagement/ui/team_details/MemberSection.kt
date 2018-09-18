package com.zcorp.opensportmanagement.ui.team_details

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.TeamMember
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import kotlinx.android.synthetic.main.rv_header_member.view.tv_header_member
import kotlinx.android.synthetic.main.rv_item_team_member.view.tv_team_member_name


class MemberSection(
        private val sectionTitle: String,
        private var mTeamMembers: List<TeamMember>
) : StatelessSection(SectionParameters.builder()
        .itemResourceId(R.layout.rv_item_team_member)
        .headerResourceId(R.layout.rv_header_member)
        .build()) {

    override fun getContentItemsTotal(): Int {
        return mTeamMembers.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        // return a custom instance of ViewHolder for the items of this section
        return MemberViewHolder(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return HeaderViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MemberViewHolder).bind(mTeamMembers[position])
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        (holder as HeaderViewHolder).bind(sectionTitle)
    }

    private inner class HeaderViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        private val headerTitle: TextView = mView.tv_header_member
        fun bind(title: String) {
            headerTitle.text = title
        }
    }

    private inner class MemberViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        private val mContentView: TextView = mView.tv_team_member_name

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }

        fun bind(member: TeamMember) {
            mContentView.text = "${member.firstName} ${member.lastName}"
        }
    }
}