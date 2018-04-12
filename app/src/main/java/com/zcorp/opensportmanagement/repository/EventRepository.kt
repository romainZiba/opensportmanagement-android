package com.zcorp.opensportmanagement.repository

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.db.EventDao
import com.zcorp.opensportmanagement.dto.EventDto
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


/**
 * EventRepository is responsible for providing a clean API for ViewModel so that viewmodel does not
 * know where the data come from
 */
class EventRepository @Inject constructor(
        private val mEventApi: EventApi,
        private val mEventDao: EventDao,
        private val mSchedulerProvider: SchedulerProvider,
        private val mLogger: ILogger) {

    companion object {
        private val TAG = EventRepository::class.java.simpleName
    }

    val eventsResource: PublishSubject<Resource<List<Event>>> = PublishSubject.create<Resource<List<Event>>>()

    fun loadEvents(teamId: Int, forceRefresh: Boolean = false) {
        var forceRemoteFetch = forceRefresh
        eventsResource.onNext(Resource.loading(true))
        // Events will always be provided by the Publisher from the DAO
        mEventDao.loadEvents(teamId).subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    if (isRemoteFetchRequired(it, forceRemoteFetch)) {
                        forceRemoteFetch = false
                        refreshEvents(teamId)
                    } else {
                        eventsResource.onNext(Resource.loading(false))
                    }
                    eventsResource.onNext(Resource.success(it))
                }, {
                    handleError(it)
                })
    }

    private fun isRemoteFetchRequired(it: List<Event>, remoteFetch: Boolean) =
            it.isEmpty() || remoteFetch

    private fun refreshEvents(teamId: Int) {
        eventsResource.onNext(Resource.loading(true))
        mEventApi.getEvents(teamId).subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe({
                    saveEvents(it)
                }, {
                    handleError(it)
                })
    }

    @WorkerThread
    private fun saveEvents(events: List<Event>) {
        Completable.fromAction {
            mEventDao.saveEvents(events)
        }.subscribeOn(mSchedulerProvider.io())
                .subscribe()
    }

    @MainThread
    private fun handleError(error: Throwable) {
        eventsResource.onNext(Resource.loading(false))
        eventsResource.onNext(Resource.failure(error))
    }

    fun createEvent(eventDto: EventDto) {
        mEventApi.createEvent(eventDto).subscribeOn(mSchedulerProvider.io())
                .subscribe({
                    mEventDao.saveEvent(it)
                }, {
                    mLogger.d(TAG, "Failed to save event: $it")
                })
    }
}