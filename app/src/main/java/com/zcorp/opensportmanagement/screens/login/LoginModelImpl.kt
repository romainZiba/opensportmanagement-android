package com.zcorp.opensportmanagement.screens.login

import android.text.TextUtils
import com.zcorp.opensportmanagement.api.UserApi
import io.reactivex.Observable

class LoginModelImpl(private val userApi: UserApi) : LoginModel {

    override fun login(username: String, password: String): Observable<Boolean> {

        if (TextUtils.isEmpty(username)) {
            return Observable.just(false)
        }
        if (TextUtils.isEmpty(password)) {
            return Observable.just(false)
        }
        return userApi.login(username, password)
    }
}
