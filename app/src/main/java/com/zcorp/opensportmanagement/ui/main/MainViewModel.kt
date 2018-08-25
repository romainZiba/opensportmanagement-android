package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.repository.TeamRepository
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with

class MainViewModel : RxViewModel() {

    lateinit var mUserRepository: UserRepository
    lateinit var mTeamRepository: TeamRepository
    lateinit var mSchedulerProvider: SchedulerProvider

    private val mTeams = MutableLiveData<State<List<Team>>>()
    val teams: LiveData<State<List<Team>>>
        get() = mTeams

    private val mLoggedState = MutableLiveData<Boolean>()
    val loggedState: LiveData<Boolean>
        get() = mLoggedState

    fun getLoggedState() {
        launch {
            mUserRepository.userLoggedObservable
                    .with(mSchedulerProvider)
                    .subscribe { logged ->
                        mLoggedState.value = logged
                    }
        }
    }

    fun getTeams() {
        mTeams.value = State.loading(true)
        launch {
            mTeamRepository.getTeams()
                    .with(mSchedulerProvider)
                    .subscribe({
                        mTeams.value = State.success(it)
                    }, {
                        mTeams.value = State.failure(it)
                    })
        }
    }
}