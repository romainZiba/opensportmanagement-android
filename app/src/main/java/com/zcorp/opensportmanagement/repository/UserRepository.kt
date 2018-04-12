package com.zcorp.opensportmanagement.repository

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.TeamApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.model.LoginRequest
import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.User
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * UserRepository is responsible for providing a clean API for ViewModel so that viewmodel does not
 * know where the data come from
 */
class UserRepository @Inject constructor(
        private val mUserApi: UserApi,
        private val mTeamApi: TeamApi,
        private val mPreferences: IPreferencesHelper,
        private val mSchedulerProvider: SchedulerProvider,
        private val mLogger: ILogger) {

    companion object {
        private val TAG = EventRepository::class.java.simpleName
    }

    val userResource: PublishSubject<Resource<User>> = PublishSubject.create<Resource<User>>()

    fun login(username: String, password: String) {
        userResource.onNext(Resource.loading(true))
        val loginRequest = LoginRequest(username, password)
        mUserApi.login(loginRequest)
                .toSingleDefault(true)
                .flatMap { mUserApi.whoAmI() }
                .flatMap { user: User ->
                    userResource.onNext(Resource.success(user))
                    // Store information locally. In this case, we trust the remote server
                    saveUserDetails(user)
                    mTeamApi.getTeams()
                }
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    userResource.onNext(Resource.loading(false))
                    saveUserTeams(it)
                }, {
                    mLogger.d(TAG, "Error occurred $it")
                    handleError(it)
                })

    }

    @WorkerThread
    private fun saveUserDetails(user: User) {
        Completable.fromAction {
            mPreferences.setCurrentUserName(user.username)
            mPreferences.setCurrentUserEmail(user.email)
            mPreferences.setCurrentUserLoggedInMode(IDataManager.LoggedInMode.LOGGED_IN_MODE_SERVER)
        }.subscribeOn(mSchedulerProvider.io())
                .subscribe()
    }

    @WorkerThread
    private fun saveUserTeams(teams: List<Team>) {
        Completable.fromAction {
            if (teams.isNotEmpty()) {
                mPreferences.setAvailableTeamIds(teams.map { it._id })
            }
        }.subscribeOn(mSchedulerProvider.io())
                .subscribe()
    }

    @MainThread
    private fun handleError(error: Throwable) {
        userResource.onNext(Resource.loading(false))
        userResource.onNext(Resource.failure(error))
    }
}