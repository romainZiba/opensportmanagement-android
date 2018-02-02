package com.zcorp.opensportmanagement.model

/**
 * Created by romainz on 01/02/18.
 */
data class User(val username: String, val password: String, var teamIds: MutableSet<Int>)