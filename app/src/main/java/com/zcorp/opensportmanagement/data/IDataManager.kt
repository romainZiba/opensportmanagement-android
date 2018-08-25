package com.zcorp.opensportmanagement.data

import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.MessagesApi
import com.zcorp.opensportmanagement.data.api.TeamApi
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper

/**
 * Created by romainz on 10/02/18.
 */
interface IDataManager : IPreferencesHelper, EventApi, TeamApi, MessagesApi {
    enum class LoggedInMode(val type: Int) {
        LOGGED_OUT(0),
        LOGGED_IN_MODE_SERVER(1)
    }
}