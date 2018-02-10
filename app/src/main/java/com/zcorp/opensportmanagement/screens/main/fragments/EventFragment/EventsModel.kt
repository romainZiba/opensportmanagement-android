package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import com.zcorp.opensportmanagement.model.Event
import io.reactivex.Observable
import java.io.Serializable

/**
 * Created by romainz on 03/02/18.
 */
interface EventsModel : Serializable {
    fun provideEvents(): Observable<List<Event>>
    fun provideEventsCount(): Observable<Int>
}