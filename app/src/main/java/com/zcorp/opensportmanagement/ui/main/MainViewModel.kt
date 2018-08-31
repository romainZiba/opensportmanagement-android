package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.data.datasource.local.EventEntity
import com.zcorp.opensportmanagement.data.datasource.local.TeamEntity
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.EventRepository
import com.zcorp.opensportmanagement.repository.Resource
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.repository.Status
import com.zcorp.opensportmanagement.repository.TeamRepository
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.utils.Optional
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with
import io.reactivex.disposables.Disposable

class MainViewModel(
        private val mUserRepository: UserRepository,
        private val mTeamRepository: TeamRepository,
        private val mEventRepository: EventRepository,
        private val mPreferencesHelper: PreferencesHelper,
        private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val mTeamStates = MutableLiveData<State<List<TeamEntity>>>()
    val teams: LiveData<State<List<TeamEntity>>>
        get() = mTeamStates

    private val mStates = MutableLiveData<State<List<EventEntity>>>()
    val states: LiveData<State<List<EventEntity>>>
        get() = mStates

    private val mLoggedState = MutableLiveData<Boolean>()
    val loggedState: LiveData<Boolean>
        get() = mLoggedState

    fun getLoggedState() {
        launch {
            mUserRepository.userLoggedObservable
                    .with(mSchedulerProvider)
                    .doOnNext { logged: Optional<Boolean> -> if (logged.isNotPresent()) { getUserInformation() }  }
                    .filter { it.isPresent() }
                    .subscribe { logged ->
                        mLoggedState.value = logged.get()
                    }
        }
    }

    fun login(username: String, password: String) {
        launch {
            mUserRepository.login(username, password)
                    .with(mSchedulerProvider)
                    .subscribe()
        }
    }

    private fun getUserInformation() {
        launch {
            mUserRepository.getUserInformation()
                    .with(mSchedulerProvider)
                    .subscribe({}, {})
        }
    }

    fun getTeams() {
        mTeamStates.value = State.loading(true)
        launch {
            mTeamRepository.getTeams(forceRefresh = true)
                    .with(mSchedulerProvider)
                    .subscribe { resource: Resource<List<TeamEntity>> ->
                        when (resource.status) {
                            Status.ERROR -> mTeamStates.value = State.failure(resource.message)
                            else -> {
                                mTeamStates.value = State.success(resource.data ?: emptyList())
                            }
                        }
                    }
        }
    }

    private var eventsDisposable: Disposable? = null

    fun getEvents(forceRefresh: Boolean = false) {
        eventsDisposable?.dispose()
        mStates.value = State.loading(true)
        launch {
            eventsDisposable = mEventRepository.loadEvents(mPreferencesHelper.getCurrentTeamId(), forceRefresh)
                    .with(mSchedulerProvider)
                    .subscribe { resource ->
                        mStates.value = State.loading(false)
                        when (resource.status) {
                            Status.ERROR -> {
                                mStates.value = State.failure(resource.message)
                            }
                            else -> {
                                mStates.value = State.success(resource.data!!)
                            }
                        }
                    }
            return@launch eventsDisposable!!
        }
    }
}