package com.zcorp.opensportmanagement.data.api

import com.zcorp.opensportmanagement.model.Event
import io.reactivex.Single
import java.io.IOException

/**
 * Created by romainz on 01/02/18.
 */
interface EventApi {

    @Throws(IOException::class)
    fun getEvents(): Single<List<Event>>

    fun getEventsCount(): Single<Int>
    fun createEvent(event: Event): Single<Event>
}