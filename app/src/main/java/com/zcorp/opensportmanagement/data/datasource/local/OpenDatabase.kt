package com.zcorp.opensportmanagement.data.datasource.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.zcorp.opensportmanagement.data.datasource.local.converter.Converters
import com.zcorp.opensportmanagement.data.datasource.local.converter.LocalDateConverter
import com.zcorp.opensportmanagement.data.datasource.local.converter.LocalDateTimeConverter

@Database(entities = [EventEntity::class, TeamEntity::class], version = 1)
@TypeConverters(LocalDateConverter::class, LocalDateTimeConverter::class, Converters::class)
abstract class OpenDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun teamDao(): TeamDao
}