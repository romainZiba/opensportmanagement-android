package com.zcorp.opensportmanagement.ui.main.fragments.messages

import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.InAppMessage
import com.zcorp.opensportmanagement.ui.main.fragments.messages.adapter.IMessageViewHolder
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import javax.inject.Inject

/**
 * Created by romainz on 17/02/18.
 */
class MessagesPresenter @Inject constructor(
        val dataManager: IDataManager,
        val schedulerProvider: SchedulerProvider) : IMessagesPresenter {

    private var mMessages: List<InAppMessage> = mutableListOf()
    lateinit var mMessagesView: IMessagesView

    override fun onAttach(view: IMessagesView) {
        mMessagesView = view
    }

    override fun onDetach() {}

    override fun onBindMessageRowViewAtPosition(position: Int, holder: IMessageViewHolder) {
        holder.setMessage(mMessages[position].message)
    }

    override fun getMessagesFromApi() {
        dataManager.getMessages().subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    mMessages = it
                    mMessagesView.onMessagesAvailable()
                }, {
                    mMessagesView.showNetworkError()
                })
    }

    override fun getMessagesCount(): Int {
        return mMessages.size
    }

    override fun onPostMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMessageType(position: Int): Int {
        val message = mMessages[position]
        return when (message.from) {
            dataManager.getCurrentUserName() -> CURRENT_USER
            else -> FRIEND
        }
    }

    companion object {
        const val CURRENT_USER = 0
        const val FRIEND = 1
    }
}