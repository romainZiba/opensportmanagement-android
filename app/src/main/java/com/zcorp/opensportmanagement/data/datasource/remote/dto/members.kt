package com.zcorp.opensportmanagement.data.datasource.remote.dto

import com.zcorp.opensportmanagement.data.datasource.local.TeamMemberEntity

data class TeamMemberDto(
    val username: String,
    val firstName: String,
    val lastName: String,
    val roles: Set<Role>,
    val licenceNumber: String,
    val email: String,
    val phoneNumber: String = "",
    val teamId: Int,
    val _id: Int
)

data class TeamMemberCreationDto(
    val firstName: String,
    val lastName: String,
    val roles: Set<Role>,
    val email: String,
    val phoneNumber: String = "",
    val licenceNumber: String = ""
)

data class TeamMemberUpdateDto(
    val firstName: String,
    val lastName: String,
    val licenceNumber: String,
    val email: String,
    val phoneNumber: String
) {
    companion object {
        fun from(entity: TeamMemberEntity): TeamMemberUpdateDto {
            return TeamMemberUpdateDto(
                    firstName = entity.firstName,
                    lastName = entity.lastName,
                    phoneNumber = entity.phoneNumber,
                    email = entity.email,
                    licenceNumber = entity.licenceNumber
            )
        }
    }
}

enum class Role {
    PLAYER, COACH, ADMIN
}