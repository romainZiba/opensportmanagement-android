package com.zcorp.opensportmanagement.model

import java.util.*

abstract class Event(var name: String, var description: String, var fromDate: Date, var toDate: Date, var place: String) {

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