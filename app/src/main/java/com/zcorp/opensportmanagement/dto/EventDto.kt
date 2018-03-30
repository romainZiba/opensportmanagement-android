package com.zcorp.opensportmanagement.dto

import com.zcorp.opensportmanagement.model.TeamMember
import org.threeten.bp.LocalDateTime

data class EventDto(val name: String,
                    val description: String,
                    val fromDate: LocalDateTime,
                    val toDate: LocalDateTime,
                    val place: String,
                    val presentTeamMembers: Set<TeamMember> = setOf(),
                    val absentTeamMembers: Set<TeamMember> = setOf())