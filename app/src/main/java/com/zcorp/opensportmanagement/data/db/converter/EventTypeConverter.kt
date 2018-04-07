package com.zcorp.opensportmanagement.data.db.converter

import android.arch.persistence.room.TypeConverter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.zcorp.opensportmanagement.model.TeamMember

class EventTypeConverter {

    private val mapper = jacksonObjectMapper()

    init {
        mapper.findAndRegisterModules()
    }

    @TypeConverter
    fun stringToSetTeamMember(data: String?): Set<TeamMember> {
        return if (data == null) emptySet() else mapper.readValue(data)
    }

    @TypeConverter
    fun someObjectListToString(teamMembers: Set<TeamMember>): String {
        return mapper.writeValueAsString(teamMembers)
    }
}