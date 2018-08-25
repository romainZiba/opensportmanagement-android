package com.zcorp.opensportmanagement.repository

import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.api.TeamApi
import com.zcorp.opensportmanagement.data.db.TeamDao
import com.zcorp.opensportmanagement.data.db.TeamEntity
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.model.Team
import io.reactivex.Single

interface TeamRepository {
    fun getTeams(): Single<List<Team>>
}

class TeamRepositoryImpl(
    private val mTeamApi: TeamApi,
    private val mTeamDao: TeamDao,
    private val mPreferences: IPreferencesHelper
) : TeamRepository {

    @WorkerThread
    override fun getTeams(): Single<List<Team>> {
        return mTeamApi.getTeams()
                .doOnSuccess { teams -> saveTeams(teams) }
    }

    @WorkerThread
    private fun saveTeams(teams: List<Team>) {
            if (teams.isNotEmpty()) {
                if (shouldChangeCurrentTeam(teams.map { it._id })) {
                    mPreferences.setCurrentTeamId(teams[0]._id)
                }
                mPreferences.setAvailableTeamIds(teams.map { it._id })
                mTeamDao.saveTeams(teams.map { TeamEntity.from(it) })
            }
    }

    private fun shouldChangeCurrentTeam(teamIds: List<Int>): Boolean {
        val currentTeamId = mPreferences.getCurrentTeamId()
        return currentTeamId == -1 || !teamIds.contains(currentTeamId)
    }
}