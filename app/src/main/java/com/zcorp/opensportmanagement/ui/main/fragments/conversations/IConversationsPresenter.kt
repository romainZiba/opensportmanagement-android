package com.zcorp.opensportmanagement.ui.main.fragments.conversations

import com.zcorp.opensportmanagement.ui.base.IBasePresenter
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.adapter.IConversationViewHolder

/**
 * Created by romainz on 03/02/18.
 */
interface IConversationsPresenter : IBasePresenter<IConversationsView> {
    fun onBindConversationRowViewAtPosition(position: Int, holder: IConversationViewHolder)
    fun getConversationsFromModel()
    fun getConversationsCount(): Int
    fun onItemClicked(adapterPosition: Int)
}