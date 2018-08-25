package com.zcorp.opensportmanagement.data.api

import com.zcorp.opensportmanagement.model.LoginRequest
import com.zcorp.opensportmanagement.model.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by romainz on 01/02/18.
 */
interface UserApi {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Completable

    @GET("/accounts/me")
    fun whoAmI(): Single<User>
}

class UserApiImpl(
    private val retrofit: Retrofit
) : UserApi {
    override fun login(loginRequest: LoginRequest): Completable {
        return retrofit.create(UserApi::class.java).login(loginRequest)
    }

    override fun whoAmI(): Single<User> {
        return retrofit.create(UserApi::class.java).whoAmI()
    }
}