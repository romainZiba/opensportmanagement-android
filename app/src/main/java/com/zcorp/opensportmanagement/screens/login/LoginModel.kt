package com.zcorp.opensportmanagement.screens.login

import io.reactivex.Observable

interface LoginModel {
    fun login(username: String, password: String): Observable<Boolean>
}
