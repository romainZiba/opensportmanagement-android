package com.zcorp.opensportmanagement.ui.messages

import com.zcorp.opensportmanagement.model.InAppMessage
import com.zcorp.opensportmanagement.ui.base.IBaseView

/**
 * Created by romainz on 16/02/18.
 */
interface IMessagesView : IBaseView {
    fun showNetworkError()
    fun onMessagesAvailable(messages: List<InAppMessage>)
    fun showNewMessageIndicator()
    fun moveToEnd()
    fun clearInputMessage()
    fun showProgress()
    fun displayNewMessage(message: InAppMessage)
    fun isAtBottom(): Boolean
}