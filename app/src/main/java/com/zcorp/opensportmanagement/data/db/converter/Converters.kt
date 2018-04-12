package com.zcorp.opensportmanagement.data.db.converter

import android.arch.persistence.room.TypeConverter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.TeamMember

class Converters {

    private val mapper = jacksonObjectMapper()

    init {
        mapper.findAndRegisterModules()
    }

    @TypeConverter
    fun stringToTeamMembers(data: String?): Set<TeamMember> {
        return if (data == null) emptySet() else mapper.readValue(data)
    }

    @TypeConverter
    fun teamMembersToString(teamMembers: Set<TeamMember>): String {
        return mapper.writeValueAsString(teamMembers)
    }

    @TypeConverter
    fun stringToTeams(data: String?): List<Team> {
        return if (data == null) emptyList() else mapper.readValue(data)
    }

    @TypeConverter
    fun teamsToString(teams: List<Team>): String {
        return mapper.writeValueAsString(teams)
    }
}