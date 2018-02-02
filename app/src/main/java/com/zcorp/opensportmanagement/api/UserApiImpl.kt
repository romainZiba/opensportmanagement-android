package com.zcorp.opensportmanagement.api

import io.reactivex.Observable


/**
 * Created by romainz on 02/02/18.
 */
class UserApiImpl: UserApi {
    override fun login(username: String, password: String): Observable<Boolean> {

        val operationObservable: Observable<Boolean> = Observable.create({
            it.onNext(longRunningOperation())
            it.onComplete()
        })
        return operationObservable
    }

    fun longRunningOperation(): Boolean {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            // error
        }

        return true
    }




}