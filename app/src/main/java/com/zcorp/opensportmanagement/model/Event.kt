package com.zcorp.opensportmanagement.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.threeten.bp.LocalDateTime
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
open class Event(val _id: Int,
                 val name: String,
                 val description: String,
                 val fromDate: LocalDateTime,
                 val toDate: LocalDateTime,
                 val place: String,
                 val presentTeamMembers: MutableSet<TeamMember>,
                 val absentTeamMembers: MutableSet<TeamMember>) : Serializable {

    enum class EventType(val type: String) {
        CHAMPIONSHIP(championship),
        FRIENDLY(friendly),
        BETWEEN_US(between_us),
        TOURNAMENT(tournament),
        TRAINING(training),
        OTHER(other)
    }

    companion object {
        const val championship = "CHAMPIONSHIP"
        const val friendly = "FRIENDLY"
        const val between_us = "BETWEEN_US"
        const val tournament = "TOURNAMENT"
        const val training = "TRAINING"
        const val other = "OTHER"

    }
}

