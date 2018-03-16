package com.zcorp.opensportmanagement.model

import java.io.Serializable

/**
 * Created by romainz on 01/02/18.
 */
data class TeamMember(var username: String, var firstName: String, var lastName: String) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TeamMember

        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        return username.hashCode()
    }
}