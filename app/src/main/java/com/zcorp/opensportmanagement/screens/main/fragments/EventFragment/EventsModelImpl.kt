package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import com.zcorp.opensportmanagement.api.EventApi
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.User
import io.reactivex.Observable

/**
 * Created by romainz on 03/02/18.
 */
class EventsModelImpl(val api: EventApi) : EventsModel {
    override fun provideEvents(): Observable<List<Event>> {
        return api.getEvents(User("", "", mutableSetOf()))
    }

    override fun provideEventsCount(): Observable<Int> {
        return api.getEventsCount()
    }
}