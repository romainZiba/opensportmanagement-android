package com.zcorp.opensportmanagement.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.di.ActivityContext
import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.ui.eventdetails.EventDetailsPresenter
import com.zcorp.opensportmanagement.ui.eventdetails.IEventDetailsPresenter
import com.zcorp.opensportmanagement.ui.login.ILoginPresenter
import com.zcorp.opensportmanagement.ui.login.LoginPresenter
import com.zcorp.opensportmanagement.ui.main.IMainPresenter
import com.zcorp.opensportmanagement.ui.main.MainPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsFragment
import com.zcorp.opensportmanagement.ui.messages.IMessagesPresenter
import com.zcorp.opensportmanagement.ui.messages.MessagesPresenter
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.stomp.IStompClientProvider
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val mActivity: AppCompatActivity): BaseContextModule() {

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return mActivity
    }

    @Provides
    internal fun provideActivity(): AppCompatActivity {
        return mActivity
    }

    @Provides
    @PerActivity
    internal fun provideLoginPresenter(dataManager: IDataManager, objectMapper: ObjectMapper): ILoginPresenter {
        return LoginPresenter(dataManager, objectMapper)
    }

    @Provides
    @PerActivity
    internal fun provideMainPresenter(dataManager: IDataManager, schedulerProvider: SchedulerProvider): IMainPresenter {
        return MainPresenter(dataManager, schedulerProvider)
    }

    @Provides
    @PerActivity
    internal fun provideEventsFragment(): ConversationsFragment {
        return ConversationsFragment()
    }

    @Provides
    @PerActivity
    internal fun provideEventDetailsPresenter(dataManager: IDataManager, schedulerProvider: SchedulerProvider): IEventDetailsPresenter {
        return EventDetailsPresenter(dataManager, schedulerProvider)
    }

    @Provides
    @PerActivity
    internal fun provideMessagesPresenter(dataManager: IDataManager,
                                          schedulerProvider: SchedulerProvider,
                                          stompClientProvider: IStompClientProvider,
                                          objectMapper: ObjectMapper): IMessagesPresenter {
        return MessagesPresenter(dataManager, schedulerProvider, stompClientProvider, objectMapper)
    }
}
