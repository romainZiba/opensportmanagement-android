package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.repository.EventRepository
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.utils.rx.with

/**
 * Created by romainz on 03/02/18.
 */
class EventsViewModel(
    private val eventRepository: EventRepository,
    private val dataManager: IDataManager,
    private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    companion object {
        val TAG: String = EventsViewModel::class.java.simpleName
    }

    private val mStates = MutableLiveData<State<List<Event>>>()
    val states: LiveData<State<List<Event>>>
        get() = mStates

    fun getEvents(forceRefresh: Boolean = false) {
        mStates.value = State.loading(true)
        launch {
            eventRepository.loadEvents(dataManager.getCurrentTeamId(), forceRefresh)
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