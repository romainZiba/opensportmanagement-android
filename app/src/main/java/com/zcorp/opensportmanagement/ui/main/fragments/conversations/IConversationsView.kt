package com.zcorp.opensportmanagement.ui.main.fragments.conversations

import com.zcorp.opensportmanagement.ui.base.IBaseView

/**
 * Created by romainz on 03/02/18.
 */
interface IConversationsView : IBaseView {
    fun showNetworkError()
    fun onDataAvailable()
    fun showConversationDetails(conversationId: String)
    fun showProgress()
}