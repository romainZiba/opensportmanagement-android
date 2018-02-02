package com.zcorp.opensportmanagement.api

import io.reactivex.Observable


/**
 * Created by romainz on 01/02/18.
 */
interface UserApi {
    fun login(username: String, password: String): Observable<Boolean>
}