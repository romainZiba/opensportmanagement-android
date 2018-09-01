package com.zcorp.opensportmanagement.ui.messages

/**
 * Created by romainz on 17/02/18.
 */
interface IMessagesPresenter {
    fun getMessages()
    fun postMessage(stringMessage: String)
    fun getCurrentUserName(): String
    fun setConversationId(conversationId: String)

    companion object {
        const val CURRENT_USER = 0
        const val FRIEND = 1
    }
}