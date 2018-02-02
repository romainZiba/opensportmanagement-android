package com.zcorp.opensportmanagement.screens.teams

import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.User

interface TeamsModel {

    interface OnRetrievalFinished {
        fun onError()

        fun onSuccess()
    }

    fun provideTeams(user: User, listener: OnRetrievalFinished): List<Team>

}