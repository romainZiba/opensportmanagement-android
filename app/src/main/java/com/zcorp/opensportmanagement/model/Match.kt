package com.zcorp.opensportmanagement.model

import org.threeten.bp.LocalDateTime

class Match : Event {

    val opponent: String
    val presentPlayers: MutableSet<Player>
    val absentPlayers: MutableSet<Player>

    constructor(id: Int, name: String, description: String, fromDate: LocalDateTime, toDate: LocalDateTime, place: String,
                opponent: String, presentPlayers: MutableSet<Player>, absentPlayers: MutableSet<Player>) :
            super(id, name, description, fromDate, toDate, place) {
        this.opponent = opponent
        this.presentPlayers = presentPlayers
        this.absentPlayers = absentPlayers
    }
}