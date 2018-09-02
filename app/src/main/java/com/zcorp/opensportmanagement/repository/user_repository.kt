package com.zcorp.opensportmanagement.repository

import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.datasource.remote.api.UserApi
import com.zcorp.opensportmanagement.data.datasource.remote.dto.AccountDto
import com.zcorp.opensportmanagement.data.datasource.remote.dto.LoginRequest
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import io.reactivex.Single

/**
 * UserRepositoryImpl is responsible for providing a clean API for ViewModel so that viewmodel does not
 * know where the data come from
 */
interface UserRepository {
    fun login(username: String, password: String): Single<AccountDto>
    fun getUserInformation(): Single<AccountDto>
}

class UserRepositoryImpl(
    private val mUserApi: UserApi,
    private val mPreferences: PreferencesHelper
) : UserRepository {
    override fun login(username: String, password: String): Single<AccountDto> {
        val loginRequest = LoginRequest(username, password)
        return mUserApi.login(loginRequest)
                .toSingleDefault(true)
                .flatMap { mUserApi.whoAmI() }
                .doOnSuccess { account: AccountDto ->
                    saveUserDetails(account)
                }
    }

    override fun getUserInformation(): Single<AccountDto> {
        return mUserApi.whoAmI()
    }

    @WorkerThread
    private fun saveUserDetails(account: AccountDto) {
        mPreferences.setCurrentUserName(account.username)
        mPreferences.setCurrentUserEmail(account.email)
    }
}