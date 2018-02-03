package com.zcorp.opensportmanagement.api

import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.User
import io.reactivex.Observable

/**
 * Created by romainz on 01/02/18.
 */
interface EventApi {
    fun getEvents(user: User): Observable<List<Event>>
    fun getEventsCount(): Observable<Int>
    fun createEvent(user: User, event: Event)
}