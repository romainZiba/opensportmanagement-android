package com.zcorp.opensportmanagement.repository

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.zcorp.opensportmanagement.data.datasource.local.EventDao
import com.zcorp.opensportmanagement.data.datasource.remote.EventsDataSource
import com.zcorp.opensportmanagement.data.datasource.remote.EventsDataSourceFactory
import com.zcorp.opensportmanagement.data.datasource.remote.api.EventApi
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDto
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.BackpressureStrategy
import java.util.concurrent.Executor

/**
 * EventRepositoryImpl is responsible for providing a clean API for ViewModel so that viewmodel does not
 * know where the data come from
 */
interface EventRepository {
    fun loadEvents(teamId: Int): Listing<EventDto>
}

class EventRepositoryImpl(
    private val mEventApi: EventApi,
    private val mSchedulerProvider: SchedulerProvider,
    private val executor: Executor
) : EventRepository {

    val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(10)
            .build()

    override fun loadEvents(teamId: Int): Listing<EventDto> {
        val sourceFactory = EventsDataSourceFactory(mEventApi, executor, teamId)
        val flowablePagedList = RxPagedListBuilder(sourceFactory, config)
                .setFetchScheduler(mSchedulerProvider.io())
                .setNotifyScheduler(mSchedulerProvider.ui())
                .buildFlowable(BackpressureStrategy.BUFFER)
        val networkState = sourceFactory.sourceSubject.flatMap { t: EventsDataSource -> t.networkState }.toFlowable(BackpressureStrategy.BUFFER)
        val refreshState = sourceFactory.sourceSubject.flatMap { t: EventsDataSource -> t.initialLoad }.toFlowable(BackpressureStrategy.BUFFER)
        return Listing(
                pagedList = flowablePagedList,
                networkState = networkState,
                retry = {
                    sourceFactory.sourceSubject.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceSubject.value?.invalidate()
                },
                refreshState = refreshState)
    }
}