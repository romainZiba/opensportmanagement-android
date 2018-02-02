package com.zcorp.opensportmanagement.screens.teams

import com.zcorp.opensportmanagement.model.User

/**
 * Created by romainz on 02/02/18.
 */
interface TeamsPresenter {
    fun getTeams(user: User)
}