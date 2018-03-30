package com.zcorp.opensportmanagement.data.api

import com.zcorp.opensportmanagement.dto.EventDto
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.Match
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by romainz on 01/02/18.
 */
interface EventApi {
    @GET("/teams/{teamId}/events")
    fun getEvents(@Path("teamId") teamId: Int): Single<List<Event>>

    fun createEvent(eventDto: EventDto): Single<Event>

    fun getEvent(id: Int): Single<Event>

    fun getMatch(id: Int): Single<Match>
}