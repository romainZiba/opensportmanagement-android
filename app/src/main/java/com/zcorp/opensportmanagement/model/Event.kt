package com.zcorp.opensportmanagement.model

import org.threeten.bp.LocalDateTime

open class Event(var id: Int,
                 var name: String,
                 var description: String,
                 var fromDate: LocalDateTime,
                 var toDate: LocalDateTime,
                 var place: String) {
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

