package com.zcorp.opensportmanagement.repository

import com.zcorp.opensportmanagement.data.datasource.local.EventDao
import com.zcorp.opensportmanagement.data.datasource.local.EventEntity
import com.zcorp.opensportmanagement.data.datasource.remote.api.EventApi
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDtosPage
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Function

/**
 * EventRepositoryImpl is responsible for providing a clean API for ViewModel so that viewmodel does not
 * know where the data come from
 */
interface EventRepository {
    fun loadEvents(teamId: Int, forceRefresh: Boolean = false): Flowable<Resource<List<EventEntity>>>
}

class EventRepositoryImpl(
        private val mEventApi: EventApi,
        private val mEventDao: EventDao
) : EventRepository {

    override fun loadEvents(teamId: Int, forceRefresh: Boolean): Flowable<Resource<List<EventEntity>>> {
        return Flowable.create({ emitter ->
            object : NetworkBoundSource<List<EventEntity>, EventDtosPage>(emitter) {
                override fun shouldFetch(data: List<EventEntity>?): Boolean {
                    return data == null || data.isEmpty() || forceRefresh
                }

                override val remote: Single<EventDtosPage>
                    get() = mEventApi.getEvents(teamId)
                override val local: Flowable<List<EventEntity>>
                    get() = mEventDao.loadEvents(teamId)

                override fun saveCallResult(data: List<EventEntity>) {
                    mEventDao.saveEvents(data)
                }

                override fun mapper(): Function<EventDtosPage, List<EventEntity>> {
                    return Function { page: EventDtosPage ->
                        page._embedded?.eventDtoes?.map { EventEntity.from(it) } ?: emptyList()
                    }
                }
            }
        }, BackpressureStrategy.BUFFER)
    }
}