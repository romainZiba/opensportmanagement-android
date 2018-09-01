package com.zcorp.opensportmanagement.ui.main.fragments.team_details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.data.datasource.local.TeamMemberEntity
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.model.TeamMember
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.Resource
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.repository.Status
import com.zcorp.opensportmanagement.repository.TeamRepository
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with

class TeamDetailsViewModel(
        private val mTeamRepository: TeamRepository,
        private val mPreferencesHelper: PreferencesHelper,
        private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val mStates = MutableLiveData<State<List<TeamMember>>>()
    val states: LiveData<State<List<TeamMember>>>
        get() = mStates

    fun getTeamMembers() {
        mStates.value = State.loading(true)
        launch {
            mTeamRepository.getTeamMembers(mPreferencesHelper.getCurrentTeamId(), forceRefresh = true)
                    .with(mSchedulerProvider)
                    .subscribe { resource: Resource<List<TeamMemberEntity>> ->
                        when (resource.status) {
                            Status.ERROR -> mStates.value = State.failure(resource.message)
                            else -> {
                                mStates.value = State.success(resource.data?.map { TeamMember.fromEntity(it) }
                                        ?: emptyList())
                            }
                        }
                    }
        }
    }
}