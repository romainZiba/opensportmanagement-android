package com.zcorp.opensportmanagement.ui.main.fragments.messages

import com.zcorp.opensportmanagement.ui.base.IBasePresenter
import com.zcorp.opensportmanagement.ui.main.fragments.messages.adapter.IMessageViewHolder

/**
 * Created by romainz on 17/02/18.
 */
interface IMessagesPresenter : IBasePresenter<IMessagesView> {
    fun onBindMessageRowViewAtPosition(position: Int, holder: IMessageViewHolder)
    fun getMessagesFromApi()
    fun getMessagesCount(): Int
    fun onPostMessage()
    fun getMessageType(position: Int): Int
}