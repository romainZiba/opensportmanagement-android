package com.zcorp.opensportmanagement.model

import org.threeten.bp.LocalDateTime

class Match : Event {

    val opponent: String

    constructor(id: Int, name: String, description: String, fromDate: LocalDateTime, toDate: LocalDateTime, place: String,
                opponent: String, presentTeamMembers: MutableSet<TeamMember>, absentTeamMembers: MutableSet<TeamMember>) :
            super(id, name, description, fromDate, toDate, place, presentTeamMembers, absentTeamMembers) {
        this.opponent = opponent
    }
}