package com.zcorp.opensportmanagement.ui.main.fragments.conversations

import android.util.Log
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by romainz on 03/02/18.
 */
class ConversationsPresenter @Inject constructor(
        private val mDataManager: IDataManager,
        private val mSchedulerProvider: SchedulerProvider,
        private val mDisposables: CompositeDisposable) : IConversationsPresenter {

    companion object {
        val TAG = ConversationsPresenter::class.java.simpleName
    }

    private var mView: IConversationsView? = null

    override fun getConversations() {
        mView?.showProgress()
        mDisposables.add(mDataManager.getConversations()
                .subscribeOn(mSchedulerProvider.newThread())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    mView?.onDataAvailable(it)
                }, {
                    Log.d(TAG, "Error while retrieving conversations $it")
                    mView?.showNetworkError()
                })
        )
    }

    override fun onConversationSelected(conversationId: String) {
        mView?.showConversationDetails(conversationId)
    }

    override fun onAttach(view: IConversationsView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
        mDisposables.clear()
        mView = null
    }
}