package com.zcorp.opensportmanagement.data.datasource.remote

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.zcorp.opensportmanagement.data.datasource.remote.api.EventApi
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDto
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.repository.NetworkState
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class EventsDataSource(
        private val eventsApi: EventApi,
        private val teamId: Int,
        private val retryExecutor: Executor
) : PageKeyedDataSource<Int, EventDto>() {

    companion object {
        private const val TAG = "EventsDataSource"
    }

    val networkState = PublishSubject.create<NetworkState>()
    val initialLoad = PublishSubject.create<NetworkState>()

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, EventDto>) {
        networkState.onNext(NetworkState.LOADING)
        initialLoad.onNext(NetworkState.LOADING)
        eventsApi.getEvents(teamId = teamId, page = 0, size = params.requestedLoadSize).subscribe({ dtos ->
            networkState.onNext(NetworkState.LOADED)
            initialLoad.onNext(NetworkState.LOADED)
            if (dtos.page.number == dtos.page.totalPages) {
                callback.onResult(dtos._embedded?.eventDtoes ?: emptyList(), null, null)
            } else {
                callback.onResult(dtos._embedded?.eventDtoes ?: emptyList(), null, 1)
            }
        }, { throwable ->
            Log.d(TAG, "Error occurred ${throwable.localizedMessage}")
            val error = NetworkState.error(throwable.message ?: "unknown err")
            networkState.onNext(error)
            initialLoad.onNext(error)
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, EventDto>) {
        networkState.onNext(NetworkState.LOADING)
        eventsApi.getEvents(teamId = teamId, page = params.key, size = params.requestedLoadSize).subscribe({ dtos ->
            networkState.onNext(NetworkState.LOADED)
            if (dtos.page.number == dtos.page.totalPages) {
                callback.onResult(dtos._embedded?.eventDtoes ?: emptyList(), null)
            } else {
                callback.onResult(dtos._embedded?.eventDtoes ?: emptyList(), dtos.page.number + 1)
            }
        }, { throwable ->
            Log.d(TAG, "Error occurred ${throwable.localizedMessage}")
            networkState.onNext(NetworkState.error(throwable.message ?: "unknown err"))
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, EventDto>) {
        networkState.onNext(NetworkState.LOADING)
        eventsApi.getEvents(teamId = teamId, page = params.key, size = params.requestedLoadSize).subscribe({ dtos ->
            networkState.onNext(NetworkState.LOADED)
            if (dtos.page.number == 0) {
                callback.onResult(dtos._embedded?.eventDtoes ?: emptyList(), null)
            } else {
                callback.onResult(dtos._embedded?.eventDtoes ?: emptyList(), dtos.page.number - 1)
            }
        }, { throwable ->
            Log.d(TAG, "Error occurred ${throwable.localizedMessage}")
            networkState.onNext(NetworkState.error(throwable.message ?: "unknown err"))
        })
    }
}

class EventsDataSourceFactory(
        private val eventsApi: EventApi,
        private val executor: Executor,
        private val mPreferencesHelper: PreferencesHelper
) : DataSource.Factory<Int, EventDto>(), KoinComponent {
    val sourceSubject = BehaviorSubject.create<EventsDataSource>()
    override fun create(): DataSource<Int, EventDto> {
        val source = EventsDataSource(eventsApi, mPreferencesHelper.getCurrentTeamId(), executor)
        sourceSubject.onNext(source)
        return source
    }
}