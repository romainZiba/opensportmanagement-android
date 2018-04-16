package com.zcorp.opensportmanagement.di.module

import android.support.v4.app.Fragment
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.punctual.IPunctualEventPresenter
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.punctual.PunctualEventPresenter
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.recurrent.IRecurrentEventPresenter
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.recurrent.RecurrentEventPresenter
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.Information.EventInformationPresenter
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.Information.IEventInformationPresenter
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by romainz on 09/02/18.
 */
@Module
class FragmentModule(private val fragment: Fragment) : BaseContextModule() {

    @Provides
    @PerActivity
    internal fun provideEventInformationPresenter(dataManager: IDataManager, schedulerProvider: SchedulerProvider): IEventInformationPresenter {
        return EventInformationPresenter(dataManager, schedulerProvider)
    }

    @Provides
    @PerActivity
    internal fun providePunctualEventPresenter(): IPunctualEventPresenter {
        return PunctualEventPresenter()
    }

    @Provides
    @PerActivity
    internal fun provideRecurrentEventPresenter(): IRecurrentEventPresenter {
        return RecurrentEventPresenter()
    }
}