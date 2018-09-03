package com.zcorp.opensportmanagement.data.datasource.remote.dto

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

const val match = "MATCH"
const val other = "OTHER"
const val training = "TRAINING"

data class EventDtos(
    val _embedded: Embedded? = null,
    val _links: Links,
    val page: Page
)

data class Page(
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int
)

data class Embedded(
    val eventDtoes: List<EventDto>
)

data class Links(
    val self: Link,
    val last: Link? = null,
    val first: Link? = null,
    val prev: Link? = null,
    val next: Link? = null
)

data class Link(
    val href: String
)

data class EventDto(
    val _id: Int,
    val name: String,
    val fromDateTime: LocalDateTime,
    val toDateTime: LocalDateTime?,
    val placeId: Int,
    val placeName: String,
    val presentMembers: List<TeamMemberDto>,
    val absentMembers: List<TeamMemberDto>,
    val waitingMembers: List<TeamMemberDto>,
    val openForRegistration: Boolean,
    val cancelled: Boolean,
    val teamId: Int,
    var localTeamName: String? = null,
    var visitorTeamName: String? = null,
    var localTeamImgUrl: String? = null,
    var visitorTeamImgUrl: String? = null,
    var visitorTeamScore: Int? = null,
    var localTeamScore: Int? = null,
    var isDone: Boolean? = null
)

data class EventCreationDto(
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime,
    val placeId: Int,
    val type: EventType,
    val name: String? = null,
    val isRecurrent: Boolean = false,
    val recurrenceDays: MutableSet<DayOfWeek> = mutableSetOf()
)

data class EventModificationDto(
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val fromTime: LocalTime,
    val toTime: LocalTime,
    val placeId: Int
)

enum class EventType(val type: String) {
    MATCH(match),
    TRAINING(training),
    OTHER(other)
}