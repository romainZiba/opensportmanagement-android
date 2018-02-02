package com.zcorp.opensportmanagement.api

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by romainz on 02/02/18.
 */
class UserApiImpl: UserApi {
    override fun login(username: String, password: String): Observable<Boolean> {
        return Observable.just(true).delay(2000, TimeUnit.MILLISECONDS)
    }
}