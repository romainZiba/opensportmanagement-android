package com.zcorp.opensportmanagement.ui.messages

import com.zcorp.opensportmanagement.data.datasource.remote.api.MessagesApi
import com.zcorp.opensportmanagement.data.datasource.remote.dto.MessageDto
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import ua.naiksoftware.stomp.client.StompClient
import java.io.Serializable

/**
 * Created by romainz on 17/02/18.
 */
class MessagesPresenter(
        private val mMessagesApi: MessagesApi,
        private val mPreferencesHelper: PreferencesHelper,
        private val mSchedulerProvider: SchedulerProvider,
        private val mDisposables: CompositeDisposable,
        private val mLogger: ILogger
) : IMessagesPresenter {

    companion object {
        val TAG = MessagesPresenter::class.java.name
    }

    private var mMessagesView: IMessagesView? = null
    private lateinit var mStompClient: StompClient
    private lateinit var mConversationId: String

    override fun setConversationId(conversationId: String) {
        this.mConversationId = conversationId
    }

//    override fun onAttach(view: IMessagesView, vararg args: Serializable) {
//        this.getMessages()
//        mMessagesView = view
//        configureWebsocketConnection()
//    }

    private fun configureWebsocketConnection() {
//        mStompClient = mStompClientProvider.client("$WSSCHEME://${BuildConfig.HOST}:$PORT/messagesWS/websocket")
//        mStompClient.connect()
//        mDisposables.add(mStompClient.topic("/topic/$mConversationId")
//                .subscribeOn(mSchedulerProvider.newThread())
//                .observeOn(mSchedulerProvider.ui())
//                .subscribe(
//                        { topicMessage ->
//                            run {
//                                val message = mObjectMapper.readValue<InAppMessage>(topicMessage.payload)
//                                mMessagesView?.displayNewMessage(message)
//                                val viewAtBottom = mMessagesView?.isAtBottom()
//                                when (viewAtBottom) {
//                                    true -> mMessagesView?.moveToEnd()
//                                    false -> mMessagesView?.showNewMessageIndicator()
//                                    else -> {
//                                        mLogger.d(TAG, "View is not present anymore")
//                                    }
//                                }
//                            }
//                        },
//                        { error ->
//                            mLogger.d("From websocket", error.toString())
//                        })
//        )
//        mDisposables.add(mStompClient.lifecycle().subscribe { lifecycleEvent ->
//            when (lifecycleEvent.type) {
//                LifecycleEvent.Type.OPENED -> mLogger.d(TAG, "Stomp connection opened")
//                LifecycleEvent.Type.ERROR -> mLogger.e(TAG, "Error", lifecycleEvent.exception)
//                LifecycleEvent.Type.CLOSED -> mLogger.d(TAG, "Stomp connection closed")
//                else -> mLogger.d(TAG, "Unexpected lifecycle event '$lifecycleEvent'")
//            }
//        })
    }

//    override fun onDetach() {
//        mDisposables.clear()
//        mMessagesView = null
//        mStompClient.disconnect()
//    }

    override fun getMessages() {
        mDisposables.add(mMessagesApi.getMessagesOrderedByDate(mConversationId)
                .subscribeOn(mSchedulerProvider.newThread())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    mMessagesView?.onMessagesAvailable(it)
                }, {
                    mMessagesView?.showNetworkError()
                })
        )
    }

    override fun postMessage(stringMessage: String) {
        mDisposables.add(mMessagesApi.createMessage(mConversationId, MessageDto(stringMessage))
                .subscribeOn(mSchedulerProvider.newThread())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    // Do nothing
                }, {
                    mLogger.d(TAG, it.localizedMessage)
                    mMessagesView?.showNetworkError()
                })
        )
//        mMessagesView?.closeSoftKeyboard()
        mMessagesView?.clearInputMessage()
    }

    override fun getCurrentUserName(): String {
        return mPreferencesHelper.getCurrentUserName()
    }
}