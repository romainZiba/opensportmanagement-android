package com.zcorp.opensportmanagement.data.api

import io.reactivex.Single


/**
 * Created by romainz on 02/02/18.
 */
class FakeUserApiImpl : UserApi {

    override fun login(username: String, password: String): Single<String> {
        return Single.create({
            it.onSuccess(loginFromNetwork())
        })
    }

    private fun loginFromNetwork(): String {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            // error
        }
        return "aaabckdld"
    }
}