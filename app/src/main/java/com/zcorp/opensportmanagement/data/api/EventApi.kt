package com.zcorp.opensportmanagement.data.api

import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.Match
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

/**
 * Created by romainz on 01/02/18.
 */
interface EventApi {
    @GET("/teams/{teamId}/events")
    fun getEvents(@Path("teamId") teamId: Int): Single<List<Event>>

    @GET("/teams/{teamId}/events/count")
    fun getEventsCount(): Single<Int>

    fun createEvent(event: Event): Single<Event>

    fun getMatch(id: Int): Single<Match>
}