package com.zcorp.opensportmanagement.repository

import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.data.db.OpenDatabase
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.model.LoginRequest
import com.zcorp.opensportmanagement.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

/**
 * UserRepositoryImpl is responsible for providing a clean API for ViewModel so that viewmodel does not
 * know where the data come from
 */
interface UserRepository {
    fun login(username: String, password: String): Single<User>
    fun getUserInformation(): Single<User>
    val userLoggedObservable: Observable<Boolean>
}

class UserRepositoryImpl(
    private val mUserApi: UserApi,
    private val mPreferences: IPreferencesHelper
) : UserRepository {

    private val mUserLoggedSubject = BehaviorSubject.create<Boolean>()
    override val userLoggedObservable: Observable<Boolean>
        get() = mUserLoggedSubject

    override fun login(username: String, password: String): Single<User> {
        val loginRequest = LoginRequest(username, password)
        return mUserApi.login(loginRequest)
                .toSingleDefault(true)
                .flatMap { mUserApi.whoAmI() }
                .doOnSuccess { user: User ->
                    saveUserDetails(user)
                    mUserLoggedSubject.onNext(true)
                }
    }

    override fun getUserInformation(): Single<User> {
        return mUserApi.whoAmI()
                .doOnSuccess { mUserLoggedSubject.onNext(true) }
                .doOnError { mUserLoggedSubject.onNext(false) }
    }

    @WorkerThread
    private fun saveUserDetails(user: User) {
        mPreferences.setCurrentUserName(user.username)
        mPreferences.setCurrentUserEmail(user.email)
    }
}