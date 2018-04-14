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

    @TypeConverter
    fun stringToSport(data: String?): Team.Sport {
        return if (data == null) Team.Sport.OTHER else Team.Sport.valueOf(data)
    }

    @TypeConverter
    fun sportToString(sport: Team.Sport): String {
        return sport.toString()
    }

    @TypeConverter
    fun stringToGender(data: String?): Team.Gender {
        return if (data == null) Team.Gender.BOTH else Team.Gender.valueOf(data)
    }

    @TypeConverter
    fun genderToString(gender: Team.Gender): String {
        return gender.toString()
    }

    @TypeConverter
    fun stringToAgeGroup(data: String?): Team.AgeGroup {
        return if (data == null) Team.AgeGroup.ADULTS else Team.AgeGroup.valueOf(data)
    }

    @TypeConverter
    fun ageGroupToString(ageGroup: Team.AgeGroup): String {
        return ageGroup.toString()
    }
}