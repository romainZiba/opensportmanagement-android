package com.zcorp.opensportmanagement.data.datasource.remote.api

import com.zcorp.opensportmanagement.data.datasource.remote.dto.TeamMemberDto
import com.zcorp.opensportmanagement.dto.TeamDto
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by romainz on 01/02/18.
 */
interface TeamApi {
    @GET("/teams")
    fun getTeams(): Single<List<TeamDto>>

    @GET("/teams/{teamId}")
    fun getTeam(@Path("teamId") teamId: Int): Single<TeamDto>

    @GET("/teams/{teamId}/members")
    fun getTeamMembers(@Path("teamId") teamId: Int): Single<List<TeamMemberDto>>
}

class TeamApiImpl(private val retrofit: Retrofit) : TeamApi {
    override fun getTeams(): Single<List<TeamDto>> {
        return retrofit.create(TeamApi::class.java).getTeams()
    }

    override fun getTeam(teamId: Int): Single<TeamDto> {
        return retrofit.create(TeamApi::class.java).getTeam(teamId)
    }

    override fun getTeamMembers(teamId: Int): Single<List<TeamMemberDto>> {
        return retrofit.create(TeamApi::class.java).getTeamMembers(teamId)
    }
}