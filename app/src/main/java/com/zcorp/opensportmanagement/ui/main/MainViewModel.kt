package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.zcorp.opensportmanagement.data.datasource.local.TeamEntity
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDto
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.EventRepository
import com.zcorp.opensportmanagement.repository.Listing
import com.zcorp.opensportmanagement.repository.NetworkState
import com.zcorp.opensportmanagement.repository.Resource
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.repository.Status
import com.zcorp.opensportmanagement.repository.TeamRepository
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with
import io.reactivex.disposables.Disposable

class MainViewModel(
        private val mUserRepository: UserRepository,
        private val mTeamRepository: TeamRepository,
        private val mEventRepository: EventRepository,
        private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val mTeamStates = MutableLiveData<State<List<TeamEntity>>>()
    val teams: LiveData<State<List<TeamEntity>>>
        get() = mTeamStates

    private val mLoggedState = MutableLiveData<State<Boolean>>()
    val loggedState: LiveData<State<Boolean>>
        get() = mLoggedState

    private val mEventsResults = mEventRepository.loadEvents()

    fun login(username: String, password: String) {
        launch {
            mUserRepository.login(username, password)
                    .with(mSchedulerProvider)
                    .subscribe({
                        mLoggedState.value = State.success(true)
                    }, {
                        mLoggedState.value = State.failure(it.localizedMessage)
                    })
        }
    }

    fun getUserInformation() {
        launch {
            mUserRepository.getUserInformation()
                    .with(mSchedulerProvider)
                    .subscribe({
                        mLoggedState.value = State.success(true)
                    }, {
                        mLoggedState.value = State.failure(it.localizedMessage)
                    })
        }
    }

    private var teamsDisposable: Disposable? = null

    fun getTeams() {
        teamsDisposable?.dispose()
        mTeamStates.value = State.loading(true)
        launch {
            teamsDisposable = mTeamRepository.getTeams(forceRefresh = true)
                    .with(mSchedulerProvider)
                    .subscribe { resource: Resource<List<TeamEntity>> ->
                        when (resource.status) {
                            Status.ERROR -> {
                                mTeamStates.value = State.loading(false)
                                mTeamStates.value = State.failure(resource.message)
                            }
                            Status.LOADING -> {
                                mTeamStates.value = State.success(resource.data ?: emptyList())
                            }
                            Status.SUCCESS -> {
                                mTeamStates.value = State.loading(false)
                                mTeamStates.value = State.success(resource.data ?: emptyList())
                            }
                        }
                    }
            return@launch teamsDisposable!!
        }
    }
}