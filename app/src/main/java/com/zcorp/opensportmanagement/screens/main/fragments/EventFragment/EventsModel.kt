package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import com.zcorp.opensportmanagement.model.Event
import io.reactivex.Observable

/**
 * Created by romainz on 03/02/18.
 */
interface EventsModel {
    fun provideEvents(): Observable<List<Event>>
    fun provideEventsCount(): Observable<Int>
}