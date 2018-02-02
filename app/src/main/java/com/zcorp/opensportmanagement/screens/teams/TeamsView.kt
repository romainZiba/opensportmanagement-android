package com.zcorp.opensportmanagement.screens.teams

import com.zcorp.opensportmanagement.model.Team

/**
 * Created by romainz on 02/02/18.
 */
interface TeamsView {
    fun showTeams(teams: List<Team>)
    fun showNetworkError()
}