package com.zcorp.opensportmanagement.data.api

import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by romainz on 01/02/18.
 */
interface TeamApi {
    @GET("/teams")
    fun getTeams(): Single<List<Team>>

    @GET("/teams/{teamId}")
    fun getTeam(@Path("teamId") teamId: Int): Single<Team>

    fun getTeamMembers(): Single<List<User>>
}