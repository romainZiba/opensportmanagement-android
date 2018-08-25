package com.zcorp.opensportmanagement.repository

import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.db.EventDao
import com.zcorp.opensportmanagement.data.db.EventEntity
import com.zcorp.opensportmanagement.model.Event
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * EventRepositoryImpl is responsible for providing a clean API for ViewModel so that viewmodel does not
 * know where the data come from
 */
interface EventRepository {
    fun loadEvents(teamId: Int, forceRefresh: Boolean = false): Flowable<List<Event>>
}

class EventRepositoryImpl @Inject constructor(
    private val mEventApi: EventApi,
    private val mEventDao: EventDao
) : EventRepository {

    override fun loadEvents(teamId: Int, forceRefresh: Boolean): Flowable<List<Event>> {
        var forceRemoteFetch = forceRefresh
        return mEventDao.loadEvents(teamId)
                .map { entities -> entities.map { Event.from(it) } }
                .flatMap {
                    if (isRemoteFetchRequired(it, forceRemoteFetch)) {
                        forceRemoteFetch = false
                        return@flatMap refreshEvents(teamId)
                    }
                    return@flatMap Flowable.just(it)
                }
    }

    private fun isRemoteFetchRequired(events: List<Event>, remoteFetch: Boolean) =
            events.isEmpty() || remoteFetch

    @WorkerThread
    private fun refreshEvents(teamId: Int): Flowable<List<Event>> {
        return mEventApi.getEvents(teamId)
                .flatMapCompletable { events ->
                    Completable.fromCallable {
                        mEventDao.saveEvents(events.map { EventEntity.from(it) })
                    }
                }.subscribeOn(Schedulers.io())
                .toFlowable<List<Event>>()
                .flatMap { mEventDao.loadEvents(teamId) }
                .map { entities -> entities.map { Event.from(it) } }
    }
}