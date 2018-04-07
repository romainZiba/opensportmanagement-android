package com.zcorp.opensportmanagement.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.zcorp.opensportmanagement.data.db.converter.EventTypeConverter
import com.zcorp.opensportmanagement.data.db.converter.LocalDateConverter
import com.zcorp.opensportmanagement.data.db.converter.LocalDateTimeConverter
import com.zcorp.opensportmanagement.model.Event


@Database(entities = [(Event::class)], version = 1)
@TypeConverters(LocalDateConverter::class, LocalDateTimeConverter::class, EventTypeConverter::class)
abstract class OpenDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}