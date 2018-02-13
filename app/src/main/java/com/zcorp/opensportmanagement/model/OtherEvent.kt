package com.zcorp.opensportmanagement.model

import org.threeten.bp.LocalDateTime


class OtherEvent : Event {


    constructor(id: Int, name: String, description: String, fromDate: LocalDateTime, toDate: LocalDateTime,
                place: String) :
            super(name, description, fromDate, toDate, place, id)

    override fun toString(): String {
        return "OtherEvent() ${super.toString()}"
    }
}
