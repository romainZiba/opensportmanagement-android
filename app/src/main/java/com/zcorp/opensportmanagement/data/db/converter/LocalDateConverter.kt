package com.zcorp.opensportmanagement.data.db.converter

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.LocalDate

class LocalDateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return if (value == null) null else LocalDate.ofEpochDay(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}