package com.zcorp.opensportmanagement.di.module

import android.content.Context
import android.support.v4.app.Fragment
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.di.ActivityContext
import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.punctual.IPunctualEventPresenter
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.punctual.PunctualEventPresenter
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.recurrent.IRecurrentEventPresenter
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.recurrent.RecurrentEventPresenter
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.Information.EventInformationPresenter
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.Information.IEventInformationPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.IConversationsPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.adapter.ConversationsAdapter
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.EventsAdapter
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by romainz on 09/02/18.
 */
@Module
class FragmentModule(private val fragment: Fragment) : BaseContextModule() {

    @Provides
    @PerActivity
    internal fun provideConversationsPresenter(dataManager: IDataManager,
                                               schedulerProvider: SchedulerProvider,
                                               disposables: CompositeDisposable): IConversationsPresenter {
        return ConversationsPresenter(dataManager, schedulerProvider, disposables)
    }

    @Provides
    @PerActivity
    internal fun provideEventInformationPresenter(dataManager: IDataManager, schedulerProvider: SchedulerProvider): IEventInformationPresenter {
        return EventInformationPresenter(dataManager, schedulerProvider)
    }

    @Provides
    @PerActivity
    internal fun provideConversationsAdapter(presenter: IConversationsPresenter): ConversationsAdapter {
        return ConversationsAdapter(mutableListOf(), presenter)
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