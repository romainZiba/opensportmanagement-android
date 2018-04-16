package com.zcorp.opensportmanagement.data.api

import com.zcorp.opensportmanagement.dto.MessageDto
import com.zcorp.opensportmanagement.model.Conversation
import com.zcorp.opensportmanagement.model.InAppMessage
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by romainz on 01/02/18.
 */
interface MessagesApi {
    @GET("conversations")
    fun getConversations(): Single<List<Conversation>>

    @GET("conversations/{conversationId}/messages")
    fun getMessagesOrderedByDate(@Path("conversationId") conversationId: String): Single<List<InAppMessage>>

    @POST("conversations/{conversationId}/messages")
    fun createMessage(@Path("conversationId") conversationId: String,
                      @Body messageDto: MessageDto): Single<InAppMessage>
}