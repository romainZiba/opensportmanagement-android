package com.zcorp.opensportmanagement.repository

import android.support.annotation.WorkerThread
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.TeamApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.data.db.TeamDao
import com.zcorp.opensportmanagement.data.db.TeamEntity
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.model.LoginRequest
import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * UserRepositoryImpl is responsible for providing a clean API for ViewModel so that viewmodel does not
 * know where the data come from
 */
interface UserRepository {
    fun login(username: String, password: String): Completable
    fun loadTeams(): Flowable<List<Team>>
}

class UserRepositoryImpl @Inject constructor(
    private val mUserApi: UserApi,
    private val mTeamApi: TeamApi,
    private val mTeamDao: TeamDao,
    private val mPreferences: IPreferencesHelper
) : UserRepository {

    override fun login(username: String, password: String): Completable {
        val loginRequest = LoginRequest(username, password)
        return mUserApi.login(loginRequest)
                .toSingleDefault(true)
                .flatMap { mUserApi.whoAmI() }
                .flatMap { user: User ->
                    // Store information locally. In this case, we trust the remote server
                    saveUserDetails(user)
                    mTeamApi.getTeams()
                }
                .flatMapCompletable { teams -> saveUserTeams(teams) }
    }

    override fun loadTeams(): Flowable<List<Team>> {
        return mTeamDao.loadTeams(mPreferences.getAvailableTeamIds().toIntArray())
                .flatMap {
                    if (it.isEmpty()) {
                        return@flatMap refreshTeams()
                    } else {
                        return@flatMap Flowable.just(it.map { entity -> Team.from(entity) })
                    }
                }
    }

    @WorkerThread
    private fun refreshTeams(): Flowable<List<Team>> {
        return mTeamApi.getTeams().subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    saveUserTeams(it)
                }
                .toFlowable<List<Team>>()
                .flatMap { mTeamDao.loadTeams(mPreferences.getAvailableTeamIds().toIntArray()) }
                .map { entities -> entities.map { Team.from(it) } }
    }

    @WorkerThread
    private fun saveUserDetails(user: User): Completable {
        return Completable.fromAction {
            mPreferences.setCurrentUserName(user.username)
            mPreferences.setCurrentUserEmail(user.email)
            mPreferences.setCurrentUserLoggedInMode(IDataManager.LoggedInMode.LOGGED_IN_MODE_SERVER)
        }
    }

    @WorkerThread
    private fun saveUserTeams(teams: List<Team>): Completable {
        return Completable.fromCallable {
            if (teams.isNotEmpty()) {
                if (shouldChangeCurrentTeam(teams.map { it._id })) {
                    mPreferences.setCurrentTeamId(teams[0]._id)
                }
                mPreferences.setAvailableTeamIds(teams.map { it._id })
                mTeamDao.saveTeams(teams.map { TeamEntity.from(it) })
            }
        }
    }

    private fun shouldChangeCurrentTeam(teamIds: List<Int>): Boolean {
        val currentTeamId = mPreferences.getCurrentTeamId()
        return currentTeamId == -1 || !teamIds.contains(currentTeamId)
    }
}