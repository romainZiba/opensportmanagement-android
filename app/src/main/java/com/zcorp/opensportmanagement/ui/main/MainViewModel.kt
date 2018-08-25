package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with

class MainViewModel(
    private val mUserRepository: UserRepository,
    private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val mStates = MutableLiveData<State<List<Team>>>()
    val states: LiveData<State<List<Team>>>
        get() = mStates

    fun getTeams() {
        mStates.value = State.loading(true)
        launch {
            mUserRepository.loadTeams()
                    .with(mSchedulerProvider)
                    .subscribe({
                        mStates.value = State.success(it)
                    }, {
                        mStates.value = State.failure(it)
                    })
        }
    }
}