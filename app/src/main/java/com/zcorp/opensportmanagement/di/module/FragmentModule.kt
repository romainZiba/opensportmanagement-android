package com.zcorp.opensportmanagement.di.module

import android.content.Context
import android.support.v4.app.Fragment
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.IConversationsPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.events.IEventsPresenter
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by romainz on 09/02/18.
 */
@Module
class FragmentModule(private val mFragment: Fragment) : BaseContextModule() {

    @Provides
    internal fun provideContext(): Context {
        return mFragment.activity!!
    }

    @Provides
    @PerActivity
    internal fun provideEventsPresenter(dataManager: IDataManager, schedulerProvider: SchedulerProvider): IEventsPresenter {
        return EventsPresenter(dataManager, schedulerProvider)
    }

    @Provides
    @PerActivity
    internal fun provideConversationsPresenter(dataManager: IDataManager, schedulerProvider: SchedulerProvider): IConversationsPresenter {
        return ConversationsPresenter(dataManager, schedulerProvider)
    }

}