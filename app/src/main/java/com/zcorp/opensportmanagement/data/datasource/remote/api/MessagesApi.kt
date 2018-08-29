package com.zcorp.opensportmanagement.data.datasource.remote.api

import com.zcorp.opensportmanagement.data.datasource.remote.dto.MessageDto
import com.zcorp.opensportmanagement.model.Conversation
import com.zcorp.opensportmanagement.model.InAppMessage
import io.reactivex.Single
import retrofit2.Retrofit
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
    fun createMessage(
        @Path("conversationId") conversationId: String,
        @Body messageDto: MessageDto
    ): Single<InAppMessage>
}

class MessagesApiImpl(private val retrofit: Retrofit): MessagesApi {
    override fun getConversations(): Single<List<Conversation>> {
        return retrofit.create(MessagesApi::class.java).getConversations()
    }

    override fun getMessagesOrderedByDate(conversationId: String): Single<List<InAppMessage>> {
        return retrofit.create(MessagesApi::class.java).getMessagesOrderedByDate(conversationId)
    }

    override fun createMessage(conversationId: String, messageDto: MessageDto): Single<InAppMessage> {
        return retrofit.create(MessagesApi::class.java).createMessage(conversationId, messageDto)
    }
}