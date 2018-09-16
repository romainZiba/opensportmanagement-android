package com.zcorp.opensportmanagement.ui.team_details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zcorp.opensportmanagement.ui.base.BaseRecyclerViewAdapter
import com.zcorp.opensportmanagement.ui.base.RecyclerViewClickListener
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.TeamMember
import kotlinx.android.synthetic.main.rv_item_team_member.view.tv_team_member_name

class TeamMembersAdapter(
    private val mValues: List<TeamMember>,
    mListener: RecyclerViewClickListener
) : BaseRecyclerViewAdapter<TeamMember, TeamMembersAdapter.ViewHolder>(mValues, mListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_team_member, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = mValues[position]
        holder.mContentView.text = "${item.firstName} ${item.lastName}"
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.tv_team_member_name

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}