package com.zcorp.opensportmanagement.model

import org.threeten.bp.LocalDateTime

abstract class Event(var name: String,
                     var description: String,
                     var fromDate: LocalDateTime,
                     var toDate: LocalDateTime,
                     var place: String) {

    override fun toString(): String {
        return "Event(name='$name', description='$description')"
    }
}

const val championship = "CHAMPIONSHIP"
const val friendly = "FRIENDLY"
const val between_us = "BETWEEN_US"
const val tournament = "TOURNAMENT"
const val training = "TRAINING"
const val other = "OTHER"


enum class EventType(val type: String) {
    CHAMPIONSHIP(championship),
    FRIENDLY(friendly),
    BETWEEN_US(between_us),
    TOURNAMENT(tournament),
    TRAINING(training),
    OTHER(other)
}