package com.zcorp.opensportmanagement.repository

import com.zcorp.opensportmanagement.data.api.MessagesApi
import com.zcorp.opensportmanagement.model.Conversation
import io.reactivex.Single
import javax.inject.Inject

interface MessageRepository {
    fun loadConversations(): Single<List<Conversation>>
}

class MessageRepositoryImpl @Inject constructor(
        private val messageApi: MessagesApi) : MessageRepository {
    override fun loadConversations(): Single<List<Conversation>> {
        return messageApi.getConversations()
    }
}