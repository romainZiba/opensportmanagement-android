package com.zcorp.opensportmanagement.model

import com.zcorp.opensportmanagement.data.datasource.local.TeamMemberEntity
import com.zcorp.opensportmanagement.data.datasource.remote.dto.Role
import java.io.Serializable

/**
 * Created by romainz on 01/02/18.
 */
data class TeamMember(
        val username: String,
        val firstName: String,
        val lastName: String,
        val roles: Set<Role>
) : Serializable {
    companion object {
        fun fromEntity(entity: TeamMemberEntity) = TeamMember(
                username = entity.username,
                firstName = entity.firstName,
                lastName = entity.lastName,
                roles = entity.roles
        )
    }
}