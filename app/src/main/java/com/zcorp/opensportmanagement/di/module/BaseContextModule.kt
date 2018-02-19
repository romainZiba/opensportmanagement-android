package com.zcorp.opensportmanagement.di.module

import com.zcorp.opensportmanagement.utils.rx.AppSchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.stomp.IStompClientProvider
import com.zcorp.opensportmanagement.utils.stomp.StompClientProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by romainz on 10/02/18.
 */
@Module
abstract class BaseContextModule {
    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    internal fun provideStompClientProvider(): IStompClientProvider {
        return StompClientProvider()
    }
}