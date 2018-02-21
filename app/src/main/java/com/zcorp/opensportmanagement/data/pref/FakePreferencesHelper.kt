package com.zcorp.opensportmanagement.data.pref

import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakePreferencesHelper @Inject constructor() : IPreferencesHelper {

    var currentUser: User = User("FakePerson", "", IDataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
            "", "", mutableSetOf())

    override fun getCurrentUserName(): String {
        return currentUser.username
    }

    override fun getCurrentUserEmail(): String {
        return currentUser.email
    }

    override fun setCurrentUserEmail(email: String) {
        currentUser.email = email
    }

    override fun getCurrentUserProfilePicUrl(): String {
        return currentUser.profilePictureUrl
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String) {
        currentUser.profilePictureUrl = profilePicUrl
    }

    override fun getCurrentUserLoggedInMode(): Int {
        return currentUser.loggedInMode.ordinal
    }

    override fun setCurrentUserLoggedInMode(mode: IDataManager.LoggedInMode) {
        currentUser.loggedInMode = mode
    }

    override fun getAccessToken(): String {
        return currentUser.accessToken
    }

    override fun setAccessToken(accessToken: String) {
        currentUser.accessToken = accessToken
    }

    override fun setCurrentUserName(username: String) {
        currentUser.username = username
    }
}
