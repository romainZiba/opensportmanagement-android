package com.zcorp.opensportmanagement.data.pref

import com.zcorp.opensportmanagement.data.IDataManager

/**
 * Created by romainz on 16/02/18.
 */
interface IPreferencesHelper {

    fun getCurrentUserLoggedInMode(): Int

    fun setCurrentUserLoggedInMode(mode: IDataManager.LoggedInMode)

    fun getCurrentUserId(): Int

    fun setCurrentUserId(userId: Int)

    fun getCurrentUserName(): String

    fun setCurrentUserName(username: String)

    fun getCurrentUserEmail(): String

    fun setCurrentUserEmail(email: String)

    fun getCurrentUserProfilePicUrl(): String

    fun setCurrentUserProfilePicUrl(profilePicUrl: String)

    fun getAccessToken(): String

    fun setAccessToken(accessToken: String)

}