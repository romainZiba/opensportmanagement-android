package com.zcorp.opensportmanagement.api

import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.User
import io.reactivex.Observable
import java.io.IOException

/**
 * Created by romainz on 01/02/18.
 */
interface EventApi {

    @Throws(IOException::class)
    fun getEvents(user: User): Observable<List<Event>>
    fun getEventsCount(): Observable<Int>
    fun createEvent(user: User, event: Event)
}