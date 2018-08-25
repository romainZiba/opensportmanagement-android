package com.zcorp.opensportmanagement.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.Event
import com.zcorp.opensportmanagement.repository.FailedEvent
import com.zcorp.opensportmanagement.repository.LoadingEvent
import com.zcorp.opensportmanagement.repository.SuccessEvent
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with

class SplashViewModel(
    private val mUserRepository: UserRepository,
    private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val mLoggedState = MutableLiveData<Boolean>()
    val loggedState: LiveData<Boolean>
        get() = mLoggedState

    private val mConnectionEvents = MutableLiveData<Event>()
    val connectionEvents: LiveData<Event>
        get() = mConnectionEvents

    fun isUserLogged() {
        launch {
            mUserRepository.userLoggedObservable
                    .with(mSchedulerProvider)
                    .subscribe { logged ->
                        mLoggedState.value = logged
                    }
        }
    }

    fun login(username: String, password: String) {
        mConnectionEvents.value = LoadingEvent
        launch {
            mUserRepository.login(username, password)
                    .with(mSchedulerProvider)
                    .subscribe({
                        mConnectionEvents.value = SuccessEvent
                    }, {
                        mConnectionEvents.value = FailedEvent(it)
                    })
        }
    }
}