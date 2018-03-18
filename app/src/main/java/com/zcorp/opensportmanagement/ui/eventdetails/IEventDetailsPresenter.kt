package com.zcorp.opensportmanagement.ui.eventdetails

import com.zcorp.opensportmanagement.model.TeamMember
import com.zcorp.opensportmanagement.ui.base.IBasePresenter

interface IEventDetailsPresenter : IBasePresenter<IEventDetailsView> {
    fun getEventDetails(id: Int)
    fun getMatchDetails(id: Int)
    fun getPresentMembersCount(): Int
    fun getAbsentMembersCount(): Int
    fun getTeamMembers(): List<TeamMember>
    fun onDisplayInformation()
    fun onDisplayTeamMembers()
}
