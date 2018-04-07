package com.zcorp.opensportmanagement.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.zcorp.opensportmanagement.model.Event
import io.reactivex.Flowable


@Dao
interface EventDao {
    @Insert(onConflict = REPLACE)
    fun saveEvents(events: List<Event>)

    @Insert(onConflict = REPLACE)
    fun saveEvent(event: Event)

    @Query("SELECT * FROM event WHERE teamId = :teamId")
    fun loadEvents(teamId: Int): Flowable<List<Event>>
}