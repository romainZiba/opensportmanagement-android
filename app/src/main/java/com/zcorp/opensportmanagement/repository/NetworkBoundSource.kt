package com.zcorp.opensportmanagement.repository

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Single
import io.reactivex.SingleEmitter
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
                .subscribe {
                    emitter.onNext(it)
                    if (shouldFetch(it.data)) {
                        fetchFromNetwork()
                    }
                }
    }

    private fun fetchFromNetwork() {
        remote.map(mapper())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe({ localTypeData: LocalType ->
                    dbDataDispsable?.dispose()
                    saveCallResult(localTypeData)
                    local.map { Resource.success(localTypeData) }
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

abstract class NetworkSingleBoundSource<LocalType, RemoteType>(private val emitter: SingleEmitter<Resource<LocalType>>) {

    companion object {
        private const val TAG = "NetworkBoundSource"
    }

    abstract val remote: Single<RemoteType>

    abstract val local: Single<LocalType>

    private var dbDataDispsable: Disposable? = null

    init {
        @Suppress("LeakingThis")
        dbDataDispsable = local
                .map { localData -> Resource.loading(localData) }
                .subscribe { resource ->
                    if (shouldFetch(resource.data)) {
                        fetchFromNetwork()
                    } else {
                        emitter.onSuccess(resource)
                    }
                }
    }

    private fun fetchFromNetwork() {
        remote.map(mapper())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe({ localTypeData: LocalType ->
                    dbDataDispsable?.dispose()
                    saveCallResult(localTypeData)
                    local.map { Resource.success(localTypeData) }
                            .subscribe { resource -> emitter.onSuccess(resource) }
                }, { error ->
                    onFetchFailed(error)
                    local.map { Resource.error(error.message ?: "", it) }
                            .subscribe { resource -> emitter.onSuccess(resource) }
                })
    }

    protected fun onFetchFailed(error: Throwable) { Log.d(TAG, "An error occurred ${error.message}") }

    abstract fun shouldFetch(data: LocalType?): Boolean

    abstract fun saveCallResult(data: LocalType)

    abstract fun mapper(): Function<RemoteType, LocalType>
}