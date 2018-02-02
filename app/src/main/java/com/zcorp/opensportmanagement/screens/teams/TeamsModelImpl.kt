package com.zcorp.opensportmanagement.screens.teams

import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.User

/**
 * Created by romainz on 02/02/18.
 */
class TeamsModelImpl: TeamsModel {
    override fun provideTeams(user: User, listener: TeamsModel.OnRetrievalFinished): List<Team> {
        return emptyList()
    }
}