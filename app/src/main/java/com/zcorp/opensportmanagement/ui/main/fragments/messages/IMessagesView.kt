package com.zcorp.opensportmanagement.ui.main.fragments.messages

/**
 * Created by romainz on 16/02/18.
 */
interface IMessagesView {
    fun showNetworkError()
    fun onMessagesAvailable()
    fun showProgress()
    fun sendMessage(message: String)
}