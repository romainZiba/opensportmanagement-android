package com.zcorp.opensportmanagement.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.threeten.bp.LocalDateTime
import java.io.Serializable
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
open class Event(val _id: Int,
                 val name: String,
                 val description: String,
                 val fromDate: LocalDateTime,
                 val toDate: LocalDateTime,
                 val place: String,
                 var presentTeamMembers: Set<TeamMember> = setOf(),
                 var absentTeamMembers: Set<TeamMember> = setOf()) : Serializable {

    companion object {
        const val championship = "CHAMPIONSHIP"
        const val friendly = "FRIENDLY"
        const val between_us = "BETWEEN_US"
        const val tournament = "TOURNAMENT"
        const val training = "TRAINING"
        const val other = "OTHER"

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other::class.java) return false
        val event = other as Event
        return _id == event._id
    }

    override fun hashCode(): Int {
        return Objects.hash(_id)
    }

    override fun toString(): String {
        return "Event{" +
                "_id=" + _id +
                ", name='" + name + '\''.toString() +
                ", description='" + description + '\''.toString() +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}'.toString()
    }

    enum class EventType(val type: String) {
        CHAMPIONSHIP(championship),
        FRIENDLY(friendly),
        BETWEEN_US(between_us),
        TOURNAMENT(tournament),
        TRAINING(training),
        OTHER(other)
    }
}

