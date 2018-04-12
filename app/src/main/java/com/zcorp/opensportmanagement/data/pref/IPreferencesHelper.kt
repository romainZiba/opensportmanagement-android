package com.zcorp.opensportmanagement.data.pref

import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Team

/**
 * Created by romainz on 16/02/18.
 */
interface IPreferencesHelper {
    fun getCurrentUserLoggedInMode(): Int
    fun setCurrentUserLoggedInMode(mode: IDataManager.LoggedInMode)
    fun getCurrentUserName(): String
    fun setCurrentUserName(username: String)
    fun getCurrentUserEmail(): String
    fun setCurrentUserEmail(email: String)
    fun getCurrentUserProfilePicUrl(): String
    fun setCurrentUserProfilePicUrl(profilePicUrl: String)
    fun getAccessToken(): String
    fun setAccessToken(accessToken: String)
    fun getCurrentTeamId(): Int
    fun setCurrentTeamId(teamId: Int)
    fun setAvailableTeamIds(availableTeams: List<Int>)
}