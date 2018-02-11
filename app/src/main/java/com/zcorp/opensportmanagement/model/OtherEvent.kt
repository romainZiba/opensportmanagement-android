package com.zcorp.opensportmanagement.model

import org.joda.time.LocalDateTime


class OtherEvent : Event {


    constructor(name: String, description: String, fromDate: LocalDateTime, toDate: LocalDateTime,
                place: String) :
            super(name, description, fromDate, toDate, place)

    override fun toString(): String {
        return "OtherEvent() ${super.toString()}"
    }
}
