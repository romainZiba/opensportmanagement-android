package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.repository.EventRepository
import com.zcorp.opensportmanagement.repository.Resource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by romainz on 03/02/18.
 */
class EventsViewModel @Inject constructor(
        private val eventRepository: EventRepository,
        private val dataManager: IDataManager) : ViewModel() {

    companion object {
        val TAG: String = EventsViewModel::class.java.simpleName
    }

    // TODO: use dagger to inject in viewModel
    private val mDisposables = CompositeDisposable()

    private var eventsLiveData = MutableLiveData<Resource<List<Event>>>()

    init {
        mDisposables.add(
                eventRepository.eventsResource.subscribe({ eventsLiveData.value = it }))
        eventRepository.loadEvents(dataManager.getCurrentTeamId())
    }

    fun getEvents() = eventsLiveData

    fun forceRefreshEvents() {
        eventRepository.loadEvents(dataManager.getCurrentTeamId(), true)
    }

    override fun onCleared() {
        super.onCleared()
        mDisposables.clear()
    }
}