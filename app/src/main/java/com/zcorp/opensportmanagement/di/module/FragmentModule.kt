package com.zcorp.opensportmanagement.di.module

import android.app.Fragment
import android.content.Context
import com.zcorp.opensportmanagement.api.EventApi
import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.ui.main.fragments.EventFragment.EventsPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.EventFragment.EventsPresenterImpl
import com.zcorp.opensportmanagement.utils.rx.AppSchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by romainz on 09/02/18.
 */
@Module
class FragmentModule(private val mFragment: Fragment) {

    @Provides
    internal fun provideContext(): Context {
        return mFragment.activity
    }

    @Provides
    internal fun provideActivity(): Fragment {
        return mFragment
    }

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @PerActivity
    internal fun provideEventsPresenter(api: EventApi): EventsPresenter {
        return EventsPresenterImpl(api)
    }
}