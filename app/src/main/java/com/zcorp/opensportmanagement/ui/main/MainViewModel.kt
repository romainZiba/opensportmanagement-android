package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.zcorp.opensportmanagement.data.datasource.local.TeamEntity
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.Resource
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.repository.Status
import com.zcorp.opensportmanagement.repository.TeamRepository
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with

class MainViewModel(
        private val mUserRepository: UserRepository,
        private val mTeamRepository: TeamRepository,
        private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {
    private val TAG = "MainViewModel"

    private val mTeams = MutableLiveData<State<List<TeamEntity>>>()
    val teams: LiveData<State<List<TeamEntity>>>
        get() = mTeams

    fun getTeams() {
        mTeams.value = State.loading(true)
        launch {
            mTeamRepository.getTeams(forceRefresh = true)
                    .with(mSchedulerProvider)
                    .subscribe { resource: Resource<List<TeamEntity>> ->
                        when (resource.status) {
                            Status.ERROR -> mTeams.value = State.failure(resource.message)
                            else -> {
                                Log.d(TAG, "resource.data ${resource.data}, size ${resource.data?.size}")
                                mTeams.value = State.success(resource.data ?: emptyList())
                            }
                        }
                    }
        }
    }
}