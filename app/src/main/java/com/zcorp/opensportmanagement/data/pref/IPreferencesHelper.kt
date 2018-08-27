package com.zcorp.opensportmanagement.data.pref

/**
 * Created by romainz on 16/02/18.
 */
interface IPreferencesHelper {
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
    fun getAvailableTeamIds(): List<Int>
}