package com.zcorp.opensportmanagement.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.repository.Event
import com.zcorp.opensportmanagement.repository.FailedEvent
import com.zcorp.opensportmanagement.repository.LoadingEvent
import com.zcorp.opensportmanagement.repository.SuccessEvent
import com.zcorp.opensportmanagement.repository.UserRepositoryImpl
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.viewmodel.RxViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val mEvents = MutableLiveData<Event>()
    val events: LiveData<Event>
        get() = mEvents

    fun login(username: String, password: String) {
        mEvents.value = LoadingEvent
        launch {
            userRepository.login(username, password).subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe({
                        mEvents.value = SuccessEvent
                    }, {
                        mEvents.value = FailedEvent(it)
                    })
        }
    }
}
