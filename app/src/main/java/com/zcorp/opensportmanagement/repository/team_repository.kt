package com.zcorp.opensportmanagement.repository

import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.datasource.local.TeamDao
import com.zcorp.opensportmanagement.data.datasource.local.TeamEntity
import com.zcorp.opensportmanagement.data.datasource.local.TeamMemberEntity
import com.zcorp.opensportmanagement.data.datasource.remote.api.TeamApi
import com.zcorp.opensportmanagement.data.datasource.remote.dto.TeamMemberDto
import com.zcorp.opensportmanagement.data.datasource.remote.dto.TeamMemberUpdateDto
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.dto.TeamDto
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Function

interface TeamRepository {
    fun loadTeams(forceRefresh: Boolean): Flowable<Resource<List<TeamEntity>>>
    fun getTeamMembers(teamId: Int, forceRefresh: Boolean): Flowable<Resource<List<TeamMemberEntity>>>
    fun getTeamMemberInfo(teamId: Int, memberId: Int): Flowable<Resource<List<TeamMemberEntity>>>
    fun updateTeamMemberProfile(teamId: Int, dto: TeamMemberUpdateDto): Completable
}

class TeamRepositoryImpl(
        private val mTeamApi: TeamApi,
        private val mTeamDao: TeamDao,
        private val mPreferences: PreferencesHelper
) : TeamRepository {

    override fun updateTeamMemberProfile(teamId: Int, dto: TeamMemberUpdateDto): Completable {
        return mTeamApi.updateTeamMember(teamId, dto)
                .doOnSuccess { remoteDto ->
                    mTeamDao.updateTeamMember(TeamMemberEntity.from(remoteDto))
                }
                .ignoreElement()
    }

    @WorkerThread
    override fun loadTeams(forceRefresh: Boolean): Flowable<Resource<List<TeamEntity>>> {
        return Flowable.create({ subscriber ->
            object : NetworkBoundSource<List<TeamEntity>, List<TeamDto>>(subscriber) {
                override fun shouldFetch(data: List<TeamEntity>?): Boolean {
                    return mPreferences.isLogged() && (data == null || data.isEmpty() || forceRefresh)
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

    @WorkerThread
    override fun getTeamMembers(teamId: Int, forceRefresh: Boolean): Flowable<Resource<List<TeamMemberEntity>>> {
        return Flowable.create({ emitter ->
            object : NetworkBoundSource<List<TeamMemberEntity>, List<TeamMemberDto>>(emitter) {
                override fun shouldFetch(data: List<TeamMemberEntity>?): Boolean {
                    return mPreferences.isLogged() && (data == null || data.isEmpty() || forceRefresh)
                }

                override val remote: Single<List<TeamMemberDto>>
                    get() = mTeamApi.getTeamMembers(teamId)
                override val local: Flowable<List<TeamMemberEntity>>
                    get() = mTeamDao.loadTeamMembers(teamId)

                override fun saveCallResult(data: List<TeamMemberEntity>) {
                    mTeamDao.saveTeamMembers(data)
                }

                override fun mapper(): Function<List<TeamMemberDto>, List<TeamMemberEntity>> {
                    return Function { list: List<TeamMemberDto> -> list.map { TeamMemberEntity.from(it) } }
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    @WorkerThread
    override fun getTeamMemberInfo(teamId: Int, memberId: Int): Flowable<Resource<List<TeamMemberEntity>>> {
        var teamMemberId = memberId
        return Flowable.create({ emitter ->
            object : NetworkBoundSource<List<TeamMemberEntity>, TeamMemberDto>(emitter) {

                override val remote: Single<TeamMemberDto>
                    get() = mTeamApi.whoAmI(teamId)
                override val local: Flowable<List<TeamMemberEntity>>
                    get() = mTeamDao.getDistincMemberById(teamMemberId)

                override fun shouldFetch(data: List<TeamMemberEntity>?): Boolean {
                    return mPreferences.isLogged()
                }

                override fun saveCallResult(data: List<TeamMemberEntity>) {
                    val member = data.firstOrNull()
                    if (member != null) {
                        teamMemberId = member._id
                        mPreferences.setCurrentTeamMemberId(teamMemberId)
                        mTeamDao.saveTeamMember(member)
                    }
                }

                override fun mapper(): Function<TeamMemberDto, List<TeamMemberEntity>> {
                    return Function { dto: TeamMemberDto -> listOf(TeamMemberEntity.from(dto)) }
                }
            }
        }, BackpressureStrategy.BUFFER)
    }
}