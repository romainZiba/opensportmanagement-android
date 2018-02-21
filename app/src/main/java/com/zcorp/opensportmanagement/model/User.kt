package com.zcorp.opensportmanagement.model

import com.zcorp.opensportmanagement.data.IDataManager

/**
 * Created by romainz on 01/02/18.
 */
data class User(var username: String, var accessToken: String, var loggedInMode: IDataManager.LoggedInMode,
                var email: String, var profilePictureUrl: String, var teamIds: MutableSet<Int>)