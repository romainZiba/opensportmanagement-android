package com.zcorp.opensportmanagement.ui.main.fragments.conversations

import com.zcorp.opensportmanagement.ui.base.IBasePresenter

/**
 * Created by romainz on 03/02/18.
 */
interface IConversationsPresenter : IBasePresenter<IConversationsView> {
    fun getConversations()
    fun onConversationSelected(conversationId: String)
}