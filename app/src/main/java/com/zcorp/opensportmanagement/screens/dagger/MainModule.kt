package com.zcorp.opensportmanagement.screens.dagger

import com.zcorp.opensportmanagement.api.EventApi
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.*
import dagger.Module
import dagger.Provides

/**
 * Created by romainz on 08/02/18.
 */
@Module
class MainModule(var context: EventFragment) {

    @MainScope
    @Provides
    internal fun provideEventFragment(): EventFragment {
        return context
    }

    @MainScope
    @Provides
    internal fun provideEventsPresenter(view: EventsView, model: EventsModel): EventsPresenter {
        return EventsPresenterImpl(view, model)
    }

    @MainScope
    @Provides
    internal fun provideEventsView(eventsFragment: EventFragment): EventsView {
        return eventsFragment
    }

    @MainScope
    @Provides
    internal fun provideEventModel(api: EventApi): EventsModel {
        return EventsModelImpl(api)
    }


}