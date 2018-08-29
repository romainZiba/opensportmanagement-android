package com.zcorp.opensportmanagement.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.data.datasource.local.OpenDatabase
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.utils.Optional
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with
import io.reactivex.Completable

class SplashViewModel(
        private val mUserRepository: UserRepository,
        private val mSchedulerProvider: SchedulerProvider,
        private val database: OpenDatabase
) : RxViewModel() {

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

    fun getUserInformation() {
        launch {
            mUserRepository.getUserInformation()
                    .with(mSchedulerProvider)
                    .subscribe({}, {})
        }
    }

    fun login(username: String, password: String) {
        launch {
            mUserRepository.login(username, password)
                    .with(mSchedulerProvider)
                    .subscribe()
        }
    }

    fun clearTables() {
        launch {
            clearAllTables()
                    .with(mSchedulerProvider)
                    .subscribe()
        }
    }

    private fun clearAllTables(): Completable {
        return Completable.fromCallable { database.clearAllTables() }
    }
}