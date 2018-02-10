package com.zcorp.opensportmanagement.data.api

import io.reactivex.Single


/**
 * Created by romainz on 01/02/18.
 */
interface UserApi {
    fun login(username: String, password: String): Single<String>
}