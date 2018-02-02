package com.zcorp.opensportmanagement.api

import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.User

/**
 * Created by romainz on 01/02/18.
 */
interface TeamApi {
    fun getTeams(user: User): List<Team>
    fun getTeam(user: User, teamId: Int): Team
}