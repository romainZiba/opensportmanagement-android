package com.zcorp.opensportmanagement.model

/**
 * Created by romainz on 01/02/18.
 */
data class User(val firstName: String, val lastName: String, val username: String, val email: String,
                val phoneNumber: String) {
    var teams: List<Team> = listOf()
}