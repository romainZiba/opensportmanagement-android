package com.zcorp.opensportmanagement.data.datasource.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.zcorp.opensportmanagement.dto.TeamDto
import com.zcorp.opensportmanagement.model.Team

@Entity(tableName = "team")
data class TeamEntity(
    @PrimaryKey
    val _id: Int,
    val name: String,
    val sport: Team.Sport,
    @ColumnInfo(name = "gender_king")
    val genderKind: Team.Gender,
    @ColumnInfo(name = "age_group")
    val ageGroup: Team.AgeGroup
) {
    companion object {
        fun from(dto: TeamDto) = TeamEntity(
                dto._id,
                dto.name,
                dto.sport,
                dto.genderKind,
                dto.ageGroup
        )
    }
}
