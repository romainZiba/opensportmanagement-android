package com.zcorp.opensportmanagement.ui.messages

/**
 * Created by romainz on 16/02/18.
 */
interface IMessagesView {
    fun showNetworkError()
    fun onMessagesAvailable()
    fun showNewMessageIndicator()
    fun scrollToPosition(position: Int)
    fun closeKeyboardAndClear()
    fun showProgress()
}