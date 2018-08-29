package com.zcorp.opensportmanagement.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.zcorp.opensportmanagement.data.datasource.local.TeamEntity

/**
 * Created by romainz on 01/02/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Team(val _id: Int, val name: String, val sport: Sport, val genderKind: Gender, val ageGroup: AgeGroup) {
    companion object {
        fun from(entity: TeamEntity) = Team(
                entity._id,
                entity.name,
                entity.sport,
                entity.genderKind,
                entity.ageGroup
        )
    }

    enum class Sport {
        BASKETBALL, HANDBALL, FOOTBALL, OTHER
    }

    enum class Gender {
        MALE, FEMALE, BOTH
    }

    enum class AgeGroup {
        U5, U6, U7, ADULTS
    }
}
