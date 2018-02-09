package com.zcorp.opensportmanagement.model

import java.util.*

class Match : Event {

    val opponent: String
    val presentPlayers: MutableSet<String>
    val notPresentPlayers: MutableSet<String>

    constructor(name: String, description: String, fromDate: Date, toDate: Date, place: String,
                opponent: String) :
            super(name, description, fromDate, toDate, place) {
        this.opponent = opponent
        this.presentPlayers = mutableSetOf()
        this.notPresentPlayers = mutableSetOf()
    }
}