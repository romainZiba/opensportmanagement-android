package com.zcorp.opensportmanagement.data.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
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
        fun from(model: Team) = TeamEntity(
                model._id,
                model.name,
                model.sport,
                model.genderKind,
                model.ageGroup
        )
    }
}
