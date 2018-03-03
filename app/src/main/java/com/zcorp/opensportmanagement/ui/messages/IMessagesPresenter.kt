package com.zcorp.opensportmanagement.ui.messages

import com.zcorp.opensportmanagement.ui.base.IBasePresenter
import com.zcorp.opensportmanagement.ui.messages.adapter.IMessageViewHolder

/**
 * Created by romainz on 17/02/18.
 */
interface IMessagesPresenter : IBasePresenter<IMessagesView> {
    fun onBindMessageRowViewAtPosition(position: Int, holder: IMessageViewHolder)
    fun getMessagesFromApi()
    fun getMessagesCount(): Int
    fun onPostMessage(stringMessage: String)
    fun getMessageType(position: Int): Int
    fun getCurrentUserName(): String
    fun setConversationId(conversationId: String)

    companion object {
        const val CURRENT_USER = 0
        const val FRIEND = 1
    }
}