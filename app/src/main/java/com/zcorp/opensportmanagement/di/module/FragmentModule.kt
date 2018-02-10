package com.zcorp.opensportmanagement.di.module

import android.app.Fragment
import android.content.Context
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.ui.main.fragments.EventFragment.IEventsPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.EventFragment.EventsPresenter
import com.zcorp.opensportmanagement.utils.rx.AppSchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by romainz on 09/02/18.
 */
@Module
class FragmentModule(private val mFragment: Fragment): BaseContextModule() {

    @Provides
    internal fun provideContext(): Context {
        return mFragment.activity
    }

    @Provides
    internal fun provideActivity(): Fragment {
        return mFragment
    }

    @Provides
    @PerActivity
    internal fun provideEventsPresenter(dataManager: IDataManager, schedulerProvider: SchedulerProvider): IEventsPresenter {
        return EventsPresenter(dataManager, schedulerProvider)
    }
}