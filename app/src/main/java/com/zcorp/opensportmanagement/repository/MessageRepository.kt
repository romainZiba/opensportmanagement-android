package com.zcorp.opensportmanagement.repository

import com.zcorp.opensportmanagement.data.api.MessagesApi
import com.zcorp.opensportmanagement.model.Conversation
import io.reactivex.Single

interface MessageRepository {
    fun loadConversations(): Single<List<Conversation>>
}

class MessageRepositoryImpl(
    private val messageApi: MessagesApi
) : MessageRepository {
    override fun loadConversations(): Single<List<Conversation>> {
        return messageApi.getConversations()
    }
}