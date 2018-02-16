package com.zcorp.opensportmanagement.model

import com.zcorp.opensportmanagement.data.IDataManager

/**
 * Created by romainz on 01/02/18.
 */
data class User(var userId: Int, var accessToken: String, var username: String, var loggedInMode: IDataManager.LoggedInMode,
                var email: String, var profilePictureUrl: String, var teamIds: MutableSet<Int>)