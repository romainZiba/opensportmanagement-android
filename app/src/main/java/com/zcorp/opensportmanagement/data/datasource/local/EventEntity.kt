package com.zcorp.opensportmanagement.data.datasource.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.zcorp.opensportmanagement.data.datasource.local.converter.Converters
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDto
import com.zcorp.opensportmanagement.data.datasource.remote.dto.TeamMemberDto
import org.threeten.bp.LocalDateTime

@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey val _id: Int,
    val name: String,
    @ColumnInfo(name = "team_id")
    val teamId: Int,
    @ColumnInfo(name = "from_date")
    val fromDateTime: LocalDateTime,
    @ColumnInfo(name = "to_date")
    val toDateTime: LocalDateTime? = null,
    val place: String,
    @ColumnInfo(name = "present_members")
    @TypeConverters(Converters::class)
    var presentTeamMembers: List<TeamMemberDto> = listOf(),
    @ColumnInfo(name = "absent_members")
    @TypeConverters(Converters::class)
    var absentTeamMembers: List<TeamMemberDto> = listOf(),
    val opponent: String? = null
) {
    companion object {
        fun from(dto: EventDto) = EventEntity(
                _id = dto._id,
                name = dto.name,
                teamId = dto.teamId,
                fromDateTime = dto.fromDateTime,
                toDateTime = dto.toDateTime,
                place = dto.placeName,
                presentTeamMembers = dto.presentMembers,
                absentTeamMembers = dto.absentMembers
        )
    }
}
