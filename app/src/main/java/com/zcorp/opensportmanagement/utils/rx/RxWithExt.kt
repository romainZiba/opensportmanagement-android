package com.zcorp.opensportmanagement.utils.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Use SchedulerProvider configuration for Observable
 */
fun Completable.with(schedulerProvider: SchedulerProvider): Completable =
        this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

/**
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.with(schedulerProvider: SchedulerProvider): Single<T> =
        this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

/**
 * Use SchedulerProvider configuration for Flowable
 */
fun <T> Flowable<T>.with(schedulerProvider: SchedulerProvider): Flowable<T> =
        this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())