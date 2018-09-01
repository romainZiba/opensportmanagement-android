package com.zcorp.opensportmanagement.model

import com.zcorp.opensportmanagement.data.datasource.local.TeamMemberEntity
import java.io.Serializable

/**
 * Created by romainz on 01/02/18.
 */
data class TeamMember(var username: String, var firstName: String, var lastName: String) : Serializable {
    companion object {
        fun fromEntity(entity: TeamMemberEntity) = TeamMember(
                username = entity.username,
                firstName = entity.firstName,
                lastName = entity.lastName
        )
    }
}