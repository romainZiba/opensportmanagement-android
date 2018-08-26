package com.zcorp.opensportmanagement.repository

import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.api.TeamApi
import com.zcorp.opensportmanagement.data.db.TeamDao
import com.zcorp.opensportmanagement.data.db.TeamEntity
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.model.Team
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Function

interface TeamRepository {
    fun getTeams(forceRefresh: Boolean): Flowable<Resource<List<TeamEntity>>>
}

class TeamRepositoryImpl(
    private val mTeamApi: TeamApi,
    private val mTeamDao: TeamDao,
    private val mPreferences: IPreferencesHelper
) : TeamRepository {

    @WorkerThread
    override fun getTeams(forceRefresh: Boolean): Flowable<Resource<List<TeamEntity>>> {
        return Flowable.create({ emitter ->
            object : NetworkBoundSource<List<TeamEntity>, List<Team>>(emitter) {
                override fun shouldFetch(data: List<TeamEntity>?): Boolean {
                    return data == null || data.isEmpty() || forceRefresh
                }

                override val remote: Single<List<Team>>
                    get() = mTeamApi.getTeams()
                override val local: Flowable<List<TeamEntity>>
                    get() = mTeamDao.loadTeams()

                override fun saveCallResult(data: List<TeamEntity>) {
                    if (data.isNotEmpty() && shouldChangeCurrentTeam(data.map { it._id })) {
                        mPreferences.setCurrentTeamId(data[0]._id)
                    }
                    mPreferences.setAvailableTeamIds(data.map { it._id })
                    mTeamDao.saveTeams(data)
                }

                override fun mapper(): Function<List<Team>, List<TeamEntity>> {
                    return Function { list: List<Team> -> list.map { TeamEntity.from(it) } }
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    private fun shouldChangeCurrentTeam(teamIds: List<Int>): Boolean {
        val currentTeamId = mPreferences.getCurrentTeamId()
        return currentTeamId == -1 || !teamIds.contains(currentTeamId)
    }
}