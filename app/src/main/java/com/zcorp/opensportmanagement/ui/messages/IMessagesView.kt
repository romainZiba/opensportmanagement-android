package com.zcorp.opensportmanagement.ui.messages

import com.zcorp.opensportmanagement.model.InAppMessage

/**
 * Created by romainz on 16/02/18.
 */
interface IMessagesView {
    fun showNetworkError()
    fun onMessagesAvailable(messages: List<InAppMessage>)
    fun showNewMessageIndicator()
    fun moveToEnd()
    fun clearInputMessage()
    fun showProgress()
    fun displayNewMessage(message: InAppMessage)
    fun isAtBottom(): Boolean
}