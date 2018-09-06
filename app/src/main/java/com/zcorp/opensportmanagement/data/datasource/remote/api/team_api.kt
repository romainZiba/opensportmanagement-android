package com.zcorp.opensportmanagement.data.datasource.remote.api

import com.zcorp.opensportmanagement.data.datasource.remote.dto.TeamMemberDto
import com.zcorp.opensportmanagement.data.datasource.remote.dto.TeamMemberUpdateDto
import com.zcorp.opensportmanagement.dto.TeamDto
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
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

    @GET("/teams/{teamId}/members/me")
    fun whoAmI(@Path("teamId") teamId: Int): Single<TeamMemberDto>

    @PUT("/teams/{teamId}/members/me")
    fun updateTeamMember(
        @Path("teamId") teamId: Int,
        @Body dto: TeamMemberUpdateDto
    ): Single<TeamMemberDto>
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

    override fun whoAmI(teamId: Int): Single<TeamMemberDto> {
        return retrofit.create(TeamApi::class.java).whoAmI(teamId)
    }

    override fun updateTeamMember(teamId: Int, dto: TeamMemberUpdateDto): Single<TeamMemberDto> {
        return retrofit.create(TeamApi::class.java).updateTeamMember(teamId, dto)
    }
}