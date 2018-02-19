package com.zcorp.opensportmanagement.di.module

import android.app.Fragment
import android.content.Context
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.events.IEventsPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.messages.IMessagesPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.messages.MessagesPresenter
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.stomp.IStompClientProvider
import dagger.Module
import dagger.Provides
import ua.naiksoftware.stomp.client.StompClient

/**
 * Created by romainz on 09/02/18.
 */
@Module
class FragmentModule(private val mFragment: Fragment) : BaseContextModule() {

    @Provides
    internal fun provideContext(): Context {
        return mFragment.activity
    }

    @Provides
    internal fun provideFragment(): Fragment {
        return mFragment
    }

    @Provides
    @PerActivity
    internal fun provideEventsPresenter(dataManager: IDataManager, schedulerProvider: SchedulerProvider): IEventsPresenter {
        return EventsPresenter(dataManager, schedulerProvider)
    }

    @Provides
    @PerActivity
    internal fun provideMessagesPresenter(dataManager: IDataManager,
                                          schedulerProvider: SchedulerProvider,
                                          stompClientProvider: IStompClientProvider): IMessagesPresenter {
        return MessagesPresenter(dataManager, schedulerProvider, stompClientProvider)
    }
}