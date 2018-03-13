package com.zcorp.opensportmanagement.ui.main.fragments.conversations

import android.util.Log
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Conversation
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.adapter.IConversationViewHolder
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import java.io.IOException
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by romainz on 03/02/18.
 */
class ConversationsPresenter @Inject constructor(val dataManager: IDataManager, val schedulerProvider: SchedulerProvider) : IConversationsPresenter {

    companion object {
        val TAG = ConversationsPresenter::class.java.simpleName
    }

    private var mConversations: List<Conversation> = listOf()
    private lateinit var mView: IConversationsView

    override fun getConversationsFromModel() {
        try {
            mView.showProgress()
            dataManager.getConversations()
                    .subscribeOn(schedulerProvider.newThread())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({
                        mConversations = it
                        mView.onDataAvailable()
                    }, {
                        Log.d(TAG, "Error while retrieving conversations $it")
                        mView.showNetworkError()
                    })
        } catch (e: IOException) {
            Log.d(TAG, "Error while retrieving conversations $e")
            mView.showNetworkError()
        }
    }

    override fun getConversationsCount(): Int {
        return mConversations.size
    }

    override fun onBindConversationRowViewAtPosition(position: Int, holder: IConversationViewHolder) {
        val conversation = mConversations[position]
        holder.setTopic(conversation.conversationTopic)
        holder.setListener()
    }

    override fun onItemClicked(adapterPosition: Int) {
        mView.showConversationDetails(mConversations[adapterPosition].conversationId)
    }

    override fun onAttach(view: IConversationsView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
    }
}