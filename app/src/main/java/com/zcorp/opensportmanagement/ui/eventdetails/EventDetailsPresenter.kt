package com.zcorp.opensportmanagement.ui.eventdetails

import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.TeamMember
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsViewModel
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by romainz on 16/03/18.
 */
class EventDetailsPresenter @Inject constructor(
        private val mDataManager: IDataManager,
        private val mSchedulerProvider: SchedulerProvider,
        private val mDisposables: CompositeDisposable,
        private val mLogger: ILogger) : IEventDetailsPresenter {

    private var mView: IEventDetailsView? = null
    private lateinit var mEventDetails: Event

    override fun getEventDetails(id: Int) {
        mDisposables.add(mDataManager.getEvent(id)
                .subscribeOn(mSchedulerProvider.newThread())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({ mEventDetails = it },
                        { mLogger.d(EventsViewModel.TAG, "Error while retrieving events $it") }
                )
        )
    }

    override fun getMatchDetails(id: Int) {
        mDisposables.add(mDataManager.getMatch(id)
                .subscribeOn(mSchedulerProvider.newThread())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({ mEventDetails = it },
                        { mLogger.d(EventsViewModel.TAG, "Error while retrieving events $it") }
                )
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
        mView?.displayInformation()
    }

    override fun onDisplayTeamMembers() {
        mView?.displayTeamMembers()
    }

    override fun onAttach(view: IEventDetailsView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
        mDisposables.clear()
        mView = null
    }


}