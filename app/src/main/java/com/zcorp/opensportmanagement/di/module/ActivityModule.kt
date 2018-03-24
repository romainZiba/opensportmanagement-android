package com.zcorp.opensportmanagement.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.di.ActivityContext
import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.ui.eventdetails.EventDetailsPresenter
import com.zcorp.opensportmanagement.ui.eventdetails.IEventDetailsPresenter
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.Information.EventInformationFragment
import com.zcorp.opensportmanagement.ui.eventdetails.fragments.members.EventMembersFragment
import com.zcorp.opensportmanagement.ui.login.ILoginPresenter
import com.zcorp.opensportmanagement.ui.login.LoginPresenter
import com.zcorp.opensportmanagement.ui.main.IMainPresenter
import com.zcorp.opensportmanagement.ui.main.MainPresenter
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationsFragment
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsFragment
import com.zcorp.opensportmanagement.ui.messages.IMessagesPresenter
import com.zcorp.opensportmanagement.ui.messages.MessagesPresenter
import com.zcorp.opensportmanagement.ui.messages.adapter.MessagesAdapter
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.stomp.IStompClientProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import kotlin.math.log

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
    internal fun provideLoginPresenter(dataManager: IDataManager,
                                       schedulerProvider: SchedulerProvider,
                                       disposables: CompositeDisposable,
                                       objectMapper: ObjectMapper): ILoginPresenter {
        return LoginPresenter(dataManager, schedulerProvider, disposables, objectMapper)
    }

    @Provides
    @PerActivity
    internal fun provideMainPresenter(dataManager: IDataManager,
                                      schedulerProvider: SchedulerProvider,
                                      disposables: CompositeDisposable): IMainPresenter {
        return MainPresenter(dataManager, schedulerProvider, disposables)
    }

    @Provides
    @PerActivity
    internal fun provideConversationsFragment(): ConversationsFragment {
        return ConversationsFragment()
    }

    @Provides
    @PerActivity
    internal fun provideEventDetailsPresenter(dataManager: IDataManager,
                                              disposables: CompositeDisposable,
                                              schedulerProvider: SchedulerProvider,
                                              logger: ILogger): IEventDetailsPresenter {
        return EventDetailsPresenter(dataManager, schedulerProvider, disposables, logger)
    }

    @Provides
    @PerActivity
    internal fun provideMessagesPresenter(dataManager: IDataManager,
                                          schedulerProvider: SchedulerProvider,
                                          disposables: CompositeDisposable,
                                          stompClientProvider: IStompClientProvider,
                                          objectMapper: ObjectMapper,
                                          logger: ILogger): IMessagesPresenter {
        return MessagesPresenter(dataManager, schedulerProvider, disposables, stompClientProvider, objectMapper, logger)
    }

    @Provides
    @PerActivity
    internal fun provideEventsFragment(): EventsFragment {
        return EventsFragment()
    }

    @Provides
    @PerActivity
    internal fun provideMessagesAdapter(presenter: IMessagesPresenter): MessagesAdapter {
        return MessagesAdapter(mutableListOf(), presenter)
    }

    @Provides
    @PerActivity
    internal fun provideEventInformationFragment(): EventInformationFragment {
        return EventInformationFragment()
    }

    @Provides
    @PerActivity
    internal fun provideEventMembersFragment(): EventMembersFragment {
        return EventMembersFragment()
    }
}
