package com.zcorp.opensportmanagement.ui.eventdetails

import android.util.Log
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.TeamMember
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsPresenter
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by romainz on 16/03/18.
 */
class EventDetailsPresenter @Inject constructor(
        val dataManager: IDataManager,
        val schedulerProvider: SchedulerProvider) : IEventDetailsPresenter {

    private lateinit var mView: IEventDetailsView

    private lateinit var mEventDetails: Event

    override fun getEventDetails(id: Int) {
        dataManager.getEvent(id).subscribe(
                {
                    mEventDetails = it
                },
                {
                    Log.d(EventsPresenter.TAG, "Error while retrieving events $it")
                }
        )
    }

    override fun getMatchDetails(id: Int) {
        dataManager.getMatch(id).subscribe(
                {
                    mEventDetails = it
                },
                {
                    Log.d(EventsPresenter.TAG, "Error while retrieving events $it")
                }
        )
    }

    override fun getPresentMembersCount(): Int {
        return mEventDetails.absentTeamMembers.size
    }

    override fun getAbsentMembersCount(): Int {
        return mEventDetails.presentTeamMembers.size
    }

    override fun getTeamMembers(): List<TeamMember> {
        val teamMembers = mutableListOf<TeamMember>()
        teamMembers.addAll(mEventDetails.presentTeamMembers)
        teamMembers.addAll(mEventDetails.absentTeamMembers)
        return teamMembers
    }

    override fun onDisplayInformation() {
        mView.displayInformation()
    }

    override fun onDisplayTeamMembers() {
        mView.displayTeamMembers()
    }

    override fun onAttach(view: IEventDetailsView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
    }


}