package com.zcorp.opensportmanagement.screens.teams

import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.User

/**
 * Created by romainz on 02/02/18.
 */
class TeamsPresenterImpl(val view: TeamsView,
                         val teamsModel: TeamsModel) : TeamsPresenter, TeamsModel.OnRetrievalFinished {

    var teams: List<Team> = emptyList()

    override fun getTeams(user: User) {
        teams = teamsModel.provideTeams(user, this)
    }

    override fun onError() {
        view.showNetworkError()
    }

    override fun onSuccess() {
        view.showTeams(teams)
    }
}