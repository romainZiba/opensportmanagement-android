package com.zcorp.opensportmanagement.data.api

import com.zcorp.opensportmanagement.model.InAppMessage
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by romainz on 01/02/18.
 */
interface MessagesApi {
    @GET("chat")
    fun getMessagesOrderedByDate(): Single<List<InAppMessage>>

    @POST("chat")
    fun createMessage(@Body message: InAppMessage): Single<InAppMessage>
}