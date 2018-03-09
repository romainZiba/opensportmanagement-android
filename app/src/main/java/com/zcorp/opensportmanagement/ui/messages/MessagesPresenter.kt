package com.zcorp.opensportmanagement.ui.messages

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.zcorp.opensportmanagement.BuildConfig
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.di.module.NetModule.Companion.PORT
import com.zcorp.opensportmanagement.di.module.NetModule.Companion.WSSCHEME
import com.zcorp.opensportmanagement.dto.MessageDto
import com.zcorp.opensportmanagement.model.InAppMessage
import com.zcorp.opensportmanagement.ui.messages.IMessagesPresenter.Companion.CURRENT_USER
import com.zcorp.opensportmanagement.ui.messages.IMessagesPresenter.Companion.FRIEND
import com.zcorp.opensportmanagement.ui.messages.adapter.IMessageViewHolder
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.stomp.IStompClientProvider
import ua.naiksoftware.stomp.LifecycleEvent
import ua.naiksoftware.stomp.client.StompClient
import javax.inject.Inject


/**
 * Created by romainz on 17/02/18.
 */
class MessagesPresenter @Inject constructor(
        private val dataManager: IDataManager,
        private val schedulerProvider: SchedulerProvider,
        private val stompClientProvider: IStompClientProvider,
        private val objectMapper: ObjectMapper) : IMessagesPresenter {

    val TAG = MessagesPresenter::class.java.name
    private var mMessages: MutableList<InAppMessage> = mutableListOf()
    private lateinit var mMessagesView: IMessagesView
    private lateinit var mStompClient: StompClient
    private lateinit var mConversationId: String

    override fun setConversationId(conversationId: String) {
        this.mConversationId = conversationId
    }

    override fun onAttach(view: IMessagesView) {
        this.getMessagesFromApi()
        mMessagesView = view
        configureWebsocketConnection()
    }

    private fun configureWebsocketConnection() {
        mStompClient = stompClientProvider.client("$WSSCHEME://${BuildConfig.HOST}:$PORT/messagesWS/websocket")
        mStompClient.connect()
        mStompClient.topic("/topic/$mConversationId")
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        { topicMessage ->
                            run {
                                addSortedMessage(objectMapper.readValue(topicMessage.payload))
                                mMessagesView.onMessagesAvailable()
                                mMessagesView.scrollToPosition(mMessages.size - 1)
                                mMessagesView.showNewMessageIndicator()
                            }
                        },
                        { error ->
                            Log.d("From websocket", error.toString())
                        })
        mStompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> Log.d(TAG, "Stomp connection opened")
                LifecycleEvent.Type.ERROR -> Log.e(TAG, "Error", lifecycleEvent.exception)
                LifecycleEvent.Type.CLOSED -> Log.d(TAG, "Stomp connection closed")
            }
        }
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
        dataManager.getMessagesOrderedByDate(mConversationId)
                .subscribeOn(schedulerProvider.newThread())
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
        dataManager.createMessage(mConversationId, MessageDto(stringMessage))
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    // Do nothing
                }, {
                    Log.d(TAG, it.localizedMessage)
                    mMessagesView.showNetworkError()
                })
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