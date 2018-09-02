package com.zcorp.opensportmanagement.data.datasource.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.zcorp.opensportmanagement.data.datasource.remote.dto.Role
import com.zcorp.opensportmanagement.data.datasource.remote.dto.TeamMemberDto

@Entity(tableName = "members")
data class TeamMemberEntity(
    @PrimaryKey val _id: Int,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "roles") val roles: Set<Role>,
    @ColumnInfo(name = "licence_number") val licenceNumber: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "team_id") val teamId: Int,
    @ColumnInfo(name = "phone_number") val phoneNumber: String? = null
) {
    companion object {
        fun from(dto: TeamMemberDto) = TeamMemberEntity(
                _id = dto._id,
                username = dto.username,
                firstName = dto.firstName,
                lastName = dto.lastName,
                roles = dto.roles,
                licenceNumber = dto.licenceNumber,
                email = dto.email,
                teamId = dto.teamId,
                phoneNumber = dto.phoneNumber
        )
    }
}
