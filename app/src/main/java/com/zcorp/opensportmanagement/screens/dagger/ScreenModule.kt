package com.zcorp.opensportmanagement.screens.dagger

import com.zcorp.opensportmanagement.api.EventApi
import com.zcorp.opensportmanagement.api.EventApiImpl
import com.zcorp.opensportmanagement.screens.main.MainActivity
import com.zcorp.opensportmanagement.screens.main.MainPresenter
import com.zcorp.opensportmanagement.screens.main.MainPresenterImpl
import com.zcorp.opensportmanagement.screens.main.MainView
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.*
import dagger.Module
import dagger.Provides

/**
 * Created by romainz on 08/02/18.
 */
@Module
class ScreenModule {

    @ScreenScope
    @Provides
    fun provideEventsPresenter(view: EventsView, model: EventsModel): EventsPresenter {
        return EventsPresenterImpl(view, model)
    }

    @ScreenScope
    @Provides
    fun provideEventsModel(api: EventApi): EventsModel {
        return EventsModelImpl(api)
    }

    @ScreenScope
    @Provides
    fun provideEventApi(): EventApi {
        return EventApiImpl()
    }

    @ScreenScope
    @Provides
    fun provideEventView(): EventsView {
        return EventFragment()
    }

    @ScreenScope
    @Provides
    fun provideMainPresenter(mainView: MainView): MainPresenter {
        return MainPresenterImpl(mainView)
    }

    @ScreenScope
    @Provides
    fun provideMainView(mainActivity: MainActivity): MainView {
        return mainActivity
    }
}