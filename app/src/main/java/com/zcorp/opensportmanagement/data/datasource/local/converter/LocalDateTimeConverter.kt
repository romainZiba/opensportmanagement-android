package com.zcorp.opensportmanagement.data.datasource.local.converter

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return if (value == null) null
        else LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault())
    }

    @TypeConverter
    fun dateToTimestamp(localDateTime: LocalDateTime?): Long? {
        return if (localDateTime == null) null
        else {
            val zdt = localDateTime.atZone(ZoneId.systemDefault())
            return zdt.toInstant().toEpochMilli()
        }
    }
}