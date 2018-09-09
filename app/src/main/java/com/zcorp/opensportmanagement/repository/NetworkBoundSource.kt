package com.zcorp.opensportmanagement.repository

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

abstract class NetworkBoundSource<LocalType, RemoteType>(private val emitter: FlowableEmitter<Resource<LocalType>>) {

    companion object {
        private const val TAG = "NetworkBoundSource"
    }

    abstract val remote: Single<RemoteType>

    abstract val local: Flowable<LocalType>

    private var dbDataDispsable: Disposable? = null

    init {
        @Suppress("LeakingThis")
        dbDataDispsable = local
                .map { localData -> Resource.loading(localData) }
                .subscribe({ resource ->
                    emitter.onNext(resource)
                    if (shouldFetch(resource.data)) {
                        fetchFromNetwork()
                    }
                }, { error ->
                    Log.d(TAG, error.toString())
                })
    }

    private fun fetchFromNetwork() {
        remote.map(mapper())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe({ remoteDataAsLocalType: LocalType ->
                    dbDataDispsable?.dispose()
                    saveCallResult(remoteDataAsLocalType)
                    local.map { localData -> Resource.success(localData) }
                            .subscribe { emitter.onNext(it) }
                }, { error ->
                    onFetchFailed(error)
                    local.map { Resource.error(error.message ?: "", it) }
                            .subscribe { emitter.onNext(it) }
                })
    }

    protected fun onFetchFailed(error: Throwable) { Log.d(TAG, "An error occurred ${error.message}") }

    abstract fun shouldFetch(data: LocalType?): Boolean

    abstract fun saveCallResult(data: LocalType)

    abstract fun mapper(): Function<RemoteType, LocalType>
}