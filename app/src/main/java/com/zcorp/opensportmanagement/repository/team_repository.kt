package com.zcorp.opensportmanagement.repository

import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.datasource.local.TeamDao
import com.zcorp.opensportmanagement.data.datasource.local.TeamEntity
import com.zcorp.opensportmanagement.data.datasource.remote.api.TeamApi
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.dto.TeamDto
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
        private val mPreferences: PreferencesHelper
) : TeamRepository {

    @WorkerThread
    override fun getTeams(forceRefresh: Boolean): Flowable<Resource<List<TeamEntity>>> {
        return Flowable.create({ emitter ->
            object : NetworkBoundSource<List<TeamEntity>, List<TeamDto>>(emitter) {
                override fun shouldFetch(data: List<TeamEntity>?): Boolean {
                    return data == null || data.isEmpty() || forceRefresh
                }

                override val remote: Single<List<TeamDto>>
                    get() = mTeamApi.getTeams()
                override val local: Flowable<List<TeamEntity>>
                    get() = mTeamDao.loadTeams()

                override fun saveCallResult(data: List<TeamEntity>) {
                    mPreferences.setAvailableTeamIds(data.map { it._id })
                    mTeamDao.saveTeams(data)
                }

                override fun mapper(): Function<List<TeamDto>, List<TeamEntity>> {
                    return Function { list: List<TeamDto> -> list.map { TeamEntity.from(it) } }
                }
            }
        }, BackpressureStrategy.BUFFER)
    }
}