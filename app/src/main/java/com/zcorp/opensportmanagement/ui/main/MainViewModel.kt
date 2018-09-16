package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.zcorp.opensportmanagement.ConnectivityRepository
import com.zcorp.opensportmanagement.ConnectivityState
import com.zcorp.opensportmanagement.data.datasource.local.TeamEntity
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDto
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
        private val mConnectivityRepo: ConnectivityRepository,
        private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val mSelectedTeamId = MutableLiveData<Int>()
    val newTeamId: LiveData<Int>
        get() = mSelectedTeamId

    private val mTeamStates = MutableLiveData<State<List<TeamEntity>>>()
    val teams: LiveData<State<List<TeamEntity>>>
        get() = mTeamStates

    private val mConnectionStates = MutableLiveData<ConnectivityState>()
    val connectivityStates: LiveData<ConnectivityState>
        get() = mConnectionStates

    private val mLoggedState = MutableLiveData<State<Boolean>>()
    val loggedState: LiveData<State<Boolean>>
        get() = mLoggedState

    private lateinit var mEventsResults: Listing<EventDto>

    private val mEventsLiveData = MutableLiveData<PagedList<EventDto>>()
    val eventsLiveData: LiveData<PagedList<EventDto>>
        get() = mEventsLiveData

    private val mNetworkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = mNetworkState

    private val mRefreshState = MutableLiveData<NetworkState>()
    val refreshState: LiveData<NetworkState>
        get() = mRefreshState

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

    fun getConnectivityStates() {
        launch {
            mConnectivityRepo.connectivityStates
                    .with(mSchedulerProvider)
                    .subscribe { mConnectionStates.value = it }
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

    fun getTeams(forceRefresh: Boolean) {
        teamsDisposable?.dispose()
        mTeamStates.value = State.loading(true)
        launch {
            teamsDisposable = mTeamRepository.loadTeams(forceRefresh)
                    .with(mSchedulerProvider)
                    .subscribe { resource: Resource<List<TeamEntity>> ->
                        when (resource.status) {
                            Status.ERROR -> {
                                mTeamStates.value = State.loading(false)
                                mTeamStates.value = State.failure(resource.message)
                            }
                            Status.LOADING -> {
                                mTeamStates.value = State.successFromDb(resource.data
                                        ?: emptyList())
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

    fun selectTeam(id: Int) {
        mSelectedTeamId.value = id
    }

    fun getEvents(teamId: Int) {
        mEventsResults = mEventRepository.loadEvents(teamId)
        val events = mEventsResults.pagedList
        val networkStates = mEventsResults.networkState
        val refreshStates = mEventsResults.refreshState

        launch {
            events.with(mSchedulerProvider)
                    .subscribe { pagedList ->
                        mEventsLiveData.value = pagedList
                    }
        }
        launch {
            networkStates.with(mSchedulerProvider)
                    .subscribe { networkState -> mNetworkState.value = networkState }
        }
        launch {
            refreshStates.with(mSchedulerProvider)
                    .subscribe { refreshState -> mRefreshState.value = refreshState }
        }
    }

    fun refreshEvents() {
        mEventsResults.refresh.invoke()
    }
}