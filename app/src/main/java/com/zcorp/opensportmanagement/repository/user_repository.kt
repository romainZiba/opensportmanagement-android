package com.zcorp.opensportmanagement.repository

import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.datasource.remote.api.UserApi
import com.zcorp.opensportmanagement.data.datasource.remote.dto.AccountDto
import com.zcorp.opensportmanagement.data.datasource.remote.dto.LoginRequest
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.model.User
import com.zcorp.opensportmanagement.utils.Optional
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

/**
 * UserRepositoryImpl is responsible for providing a clean API for ViewModel so that viewmodel does not
 * know where the data come from
 */
interface UserRepository {
    fun login(username: String, password: String): Single<AccountDto>
    fun getUserInformation(): Single<AccountDto>
    val userLoggedObservable: Observable<Optional<Boolean>>
}

class UserRepositoryImpl(
        private val mUserApi: UserApi,
        private val mPreferences: PreferencesHelper
) : UserRepository {

    private val mUserLoggedSubject = BehaviorSubject.createDefault<Optional<Boolean>>(Optional.empty())
    override val userLoggedObservable: Observable<Optional<Boolean>>
        get() = mUserLoggedSubject

    override fun login(username: String, password: String): Single<AccountDto> {
        val loginRequest = LoginRequest(username, password)
        return mUserApi.login(loginRequest)
                .toSingleDefault(true)
                .flatMap { mUserApi.whoAmI() }
                .doOnSuccess { account: AccountDto ->
                    saveUserDetails(account)
                    mUserLoggedSubject.onNext(Optional.of(true))
                }
    }

    override fun getUserInformation(): Single<AccountDto> {
        return mUserApi.whoAmI()
                .doOnSuccess { mUserLoggedSubject.onNext(Optional.of(true)) }
                .doOnError { mUserLoggedSubject.onNext(Optional.of(false)) }
    }

    @WorkerThread
    private fun saveUserDetails(account: AccountDto) {
        mPreferences.setCurrentUserName(account.username)
        mPreferences.setCurrentUserEmail(account.email)
    }
}