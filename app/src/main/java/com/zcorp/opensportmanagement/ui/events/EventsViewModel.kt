package com.zcorp.opensportmanagement.ui.events

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDto
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.EventRepository
import com.zcorp.opensportmanagement.repository.NetworkState
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with

class EventsViewModel(
    private val mEventRepository: EventRepository,
    private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {
    private val mEventsResults = mEventRepository.loadEvents()

    private val mEventsLiveData = MutableLiveData<PagedList<EventDto>>()
    val eventsLiveData: LiveData<PagedList<EventDto>>
        get() = mEventsLiveData

    private val mNetworkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = mNetworkState

    private val mRefreshState = MutableLiveData<NetworkState>()
    val refreshState: LiveData<NetworkState>
        get() = mRefreshState

    fun getEvents() {
        val events = mEventsResults.pagedList
        val networkStates = mEventsResults.networkState
        val refreshStates = mEventsResults.refreshState

        launch {
            events.with(mSchedulerProvider)
                    .subscribe { pagedList ->
                        mEventsLiveData.value = pagedList
                    }
        }
        launch {
            networkStates.with(mSchedulerProvider)
                    .subscribe { networkState -> mNetworkState.value = networkState }
        }
        launch {
            refreshStates.with(mSchedulerProvider)
                    .subscribe { refreshState -> mRefreshState.value = refreshState }
        }
    }

    fun refresh() {
        mEventsResults.refresh.invoke()
    }
}