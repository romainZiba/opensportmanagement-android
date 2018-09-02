package com.zcorp.opensportmanagement.data.datasource.remote.dto

data class TeamMemberDto(
    val username: String,
    val firstName: String,
    val lastName: String,
    val roles: Set<Role>,
    val licenceNumber: String,
    val email: String,
    val phoneNumber: String?,
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

data class TeamMemberUpdateDto(val licenceNumber: String)

enum class Role {
    PLAYER, COACH, ADMIN
}