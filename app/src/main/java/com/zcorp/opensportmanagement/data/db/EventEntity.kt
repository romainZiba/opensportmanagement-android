package com.zcorp.opensportmanagement.data.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.zcorp.opensportmanagement.data.db.converter.Converters
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.TeamMember
import org.threeten.bp.LocalDateTime

@Entity(tableName = "event")
data class EventEntity(@PrimaryKey val _id: Int,
                       val name: String,
                       val description: String,
                       @ColumnInfo(name = "team_id")
                       val teamId: Int,
                       @ColumnInfo(name = "from_date")
                       val fromDate: LocalDateTime,
                       @ColumnInfo(name = "to_date")
                       val toDate: LocalDateTime,
                       val place: String,
                       @ColumnInfo(name = "present_members")
                       @TypeConverters(Converters::class)
                       var presentTeamMembers: Set<TeamMember> = setOf(),
                       @ColumnInfo(name = "absent_members")
                       @TypeConverters(Converters::class)
                       var absentTeamMembers: Set<TeamMember> = setOf(),
                       val opponent: String?) {
    companion object {
        fun from(model: Event) = EventEntity(
                model._id,
                model.name,
                model.description,
                model.teamId,
                model.fromDate,
                model.toDate,
                model.place,
                model.presentTeamMembers,
                model.absentTeamMembers,
                model.opponent
        )
    }
}

