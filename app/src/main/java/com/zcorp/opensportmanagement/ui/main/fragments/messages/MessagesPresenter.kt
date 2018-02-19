package com.zcorp.opensportmanagement.ui.main.fragments.messages

import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.InAppMessage
import com.zcorp.opensportmanagement.ui.main.fragments.messages.IMessagesPresenter.Companion.CURRENT_USER
import com.zcorp.opensportmanagement.ui.main.fragments.messages.IMessagesPresenter.Companion.FRIEND
import com.zcorp.opensportmanagement.ui.main.fragments.messages.adapter.IMessageViewHolder
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.stomp.IStompClientProvider
import org.threeten.bp.OffsetDateTime
import ua.naiksoftware.stomp.client.StompClient
import javax.inject.Inject


/**
 * Created by romainz on 17/02/18.
 */
class MessagesPresenter @Inject constructor(
        private val dataManager: IDataManager,
        private val schedulerProvider: SchedulerProvider,
        private val stompClientProvider: IStompClientProvider) : IMessagesPresenter {

    private var mMessages: MutableList<InAppMessage> = mutableListOf()
    private lateinit var mMessagesView: IMessagesView
    private lateinit var mStompClient: StompClient

    override fun onAttach(view: IMessagesView) {
        mMessagesView = view
        mStompClient = stompClientProvider.client("ws://192.168.1.122:8090/chatWS/websocket")
        mStompClient.topic("/topic/messages")
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                { topicMessage ->
                    run {
                        val mapper = jacksonObjectMapper()
                        mapper.findAndRegisterModules()
                        addSortedMessage(mapper.readValue(topicMessage.payload))
                        mMessagesView.onMessagesAvailable()
                        mMessagesView.showNewMessageIndicator()
                    }
                },
                { error -> Log.d("From websocket", error.toString()) })
        mStompClient.connect()
    }

    override fun onDetach() {
        mStompClient.disconnect()
    }

    override fun onBindMessageRowViewAtPosition(position: Int, holder: IMessageViewHolder) {
        val message = mMessages[position]
        holder.setMessage(message.message)
        holder.setMessageUserAndDate(message.from, message.time)
    }

    override fun getMessagesFromApi() {
        dataManager.getMessagesOrderedByDate().subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    mMessages = it.toMutableList()
                    mMessagesView.onMessagesAvailable()
                }, {
                    mMessagesView.showNetworkError()
                })
    }

    override fun getMessagesCount(): Int {
        return mMessages.size
    }

    override fun onPostMessage(stringMessage: String) {
        val postedMessage = InAppMessage(stringMessage, dataManager.getCurrentUserName(), OffsetDateTime.now())
        dataManager.createMessage(postedMessage)
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe {
                    message -> addSortedMessage(message)
                    mMessagesView.onMessagesAvailable()
                    mMessagesView.scrollToPosition(mMessages.size - 1)
                }
        mMessagesView.closeKeyboardAndClear()
    }

    override fun getMessageType(position: Int): Int {
        val message = mMessages[position]
        return when (message.from) {
            dataManager.getCurrentUserName() -> CURRENT_USER
            else -> FRIEND
        }
    }

    override fun getCurrentUserName(): String {
        return dataManager.getCurrentUserName()
    }

    private fun addSortedMessage(message: InAppMessage) {
        mMessages.add(message)
        mMessages.sortBy { it.time }
    }
}