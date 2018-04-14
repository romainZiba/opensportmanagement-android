package com.zcorp.opensportmanagement.model

import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.zcorp.opensportmanagement.data.db.EventEntity
import com.zcorp.opensportmanagement.data.db.converter.Converters
import org.threeten.bp.LocalDateTime
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Event(@PrimaryKey val _id: Int,
                 val name: String,
                 val description: String,
                 val teamId: Int,
                 val fromDate: LocalDateTime,
                 val toDate: LocalDateTime,
                 val place: String,
                 @TypeConverters(Converters::class) var presentTeamMembers: Set<TeamMember> = setOf(),
                 @TypeConverters(Converters::class) var absentTeamMembers: Set<TeamMember> = setOf(),
                 val opponent: String?) : Serializable {

    companion object {
        const val championship = "CHAMPIONSHIP"
        const val friendly = "FRIENDLY"
        const val between_us = "BETWEEN_US"
        const val tournament = "TOURNAMENT"
        const val training = "TRAINING"
        const val other = "OTHER"

        fun from(entity: EventEntity) = Event(
                entity._id,
                entity.name,
                entity.description,
                entity.teamId,
                entity.fromDate,
                entity.toDate,
                entity.place,
                entity.presentTeamMembers,
                entity.absentTeamMembers,
                entity.opponent
        )

    }

    enum class EventType(val type: String) {
        CHAMPIONSHIP(championship),
        FRIENDLY(friendly),
        BETWEEN_US(between_us),
        TOURNAMENT(tournament),
        TRAINING(training),
        OTHER(other)
    }

    fun isMatch(): Boolean {
        return opponent != null
    }
}

