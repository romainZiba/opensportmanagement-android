package com.zcorp.opensportmanagement.model

import org.threeten.bp.LocalDateTime

class Match : Event {

    val opponent: String
    val presentPlayers: MutableSet<String>
    val notPresentPlayers: MutableSet<String>

    constructor(id: Int, name: String, description: String, fromDate: LocalDateTime, toDate: LocalDateTime, place: String,
                opponent: String) :
            super(id, name, description, fromDate, toDate, place) {
        this.opponent = opponent
        this.presentPlayers = mutableSetOf()
        this.notPresentPlayers = mutableSetOf()
    }
}