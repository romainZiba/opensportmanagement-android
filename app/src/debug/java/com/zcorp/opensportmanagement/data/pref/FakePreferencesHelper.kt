package com.zcorp.opensportmanagement.data.pref

import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakePreferencesHelper @Inject constructor() : IPreferencesHelper {

    var currentUser: User = User("FakePerson", "", "FK", "", "")
    var teamId: Int = 0
    private var availableTeams: List<Team> = listOf()

    override fun getCurrentUserName(): String {
        return currentUser.username
    }

    override fun getCurrentUserEmail(): String {
        return currentUser.email
    }

    override fun setCurrentUserEmail(email: String) {
        currentUser = User(currentUser.firstName, currentUser.lastName, currentUser.username, email, currentUser.phoneNumber)
    }

    override fun getCurrentUserProfilePicUrl(): String {
        return ""
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String) {
    }

    override fun getCurrentUserLoggedInMode(): Int {
        return 0
    }

    override fun setCurrentUserLoggedInMode(mode: IDataManager.LoggedInMode) {
    }

    override fun getAccessToken(): String {
        return ""
    }

    override fun setAccessToken(accessToken: String) {
    }

    override fun setCurrentUserName(username: String) {
        currentUser = User(currentUser.firstName, currentUser.lastName, username, currentUser.email, currentUser.phoneNumber)
    }

    override fun getCurrentTeamId(): Int {
        return teamId
    }

    override fun setCurrentTeamId(teamId: Int) {
        this.teamId = teamId
    }

    override fun setAvailableTeams(availableTeams: List<Team>) {
        this.availableTeams = availableTeams
    }
}
