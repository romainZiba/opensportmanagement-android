package com.zcorp.opensportmanagement.data.datasource.remote.api

import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventCreationDto
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDto
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDtos
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by romainz on 01/02/18.
 */
interface EventApi {
    @GET("/teams/{teamId}/events")
    fun getEvents(@Path("teamId") teamId: Int,
                  @Query("page") page: Int = 0,
                  @Query("size") size: Int = 5): Single<EventDtos>

    fun createEvent(eventDto: EventCreationDto): Single<EventDto>

    fun getEvent(id: Int): Single<EventDto>

    fun getMatch(id: Int): Single<EventDto>
}

class EventApiImpl(private val retrofit: Retrofit) : EventApi {
    override fun getEvent(id: Int): Single<EventDto> {
        return retrofit.create(EventApi::class.java).getEvent(id)
    }

    override fun getMatch(id: Int): Single<EventDto> {
        return retrofit.create(EventApi::class.java).getMatch(id)
    }

    override fun getEvents(teamId: Int, page: Int, size: Int): Single<EventDtos> {
        return retrofit.create(EventApi::class.java).getEvents(teamId, page, size)
    }

    override fun createEvent(eventDto: EventCreationDto): Single<EventDto> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}