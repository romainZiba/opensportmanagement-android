package com.zcorp.opensportmanagement.data

/**
 * Created by romainz on 10/02/18.
 */
interface DataManager {


    fun updateUserInfo(
            accessToken: String,
            userId: Long?,
            loggedInMode: LoggedInMode,
            userName: String,
            email: String,
            profilePicPath: String)

    enum class LoggedInMode(val type: Int) {
        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3)
    }
}