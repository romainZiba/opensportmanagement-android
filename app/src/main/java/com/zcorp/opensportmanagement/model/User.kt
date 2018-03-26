package com.zcorp.opensportmanagement.model

/**
 * Created by romainz on 01/02/18.
 */
data class User(val firstName: String, val lastName: String, val username: String, val email: String,
                val phoneNumber: String, val teamsDto: List<TeamDto>)