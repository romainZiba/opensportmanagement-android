package com.zcorp.opensportmanagement.ui.main.fragments.conversations

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.model.Conversation
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.MessageRepository
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with

/**
 * Created by romainz on 03/02/18.
 */
class ConversationViewModel(
    private val messageRepository: MessageRepository,
    private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val mStates = MutableLiveData<State<List<Conversation>>>()
    val states: LiveData<State<List<Conversation>>>
        get() = mStates

    fun getConversations() {
        mStates.value = State.loading(true)
        launch {
            messageRepository.loadConversations()
                    .with(mSchedulerProvider)
                    .subscribe({
                        mStates.value = State.loading(false)
                        mStates.value = State.success(it)
                    }, {
                        mStates.value = State.loading(false)
                        mStates.value = State.failure(it)
                    })
        }
    }
}