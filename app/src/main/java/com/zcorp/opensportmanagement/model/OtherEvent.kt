package com.zcorp.opensportmanagement.model

import java.util.*

class OtherEvent : Event {


    constructor(name: String, description: String, fromDate: Date, toDate: Date,
                place: String) :
            super(name, description, fromDate, toDate, place)

    override fun toString(): String {
        return "OtherEvent() ${super.toString()}"
    }
}
