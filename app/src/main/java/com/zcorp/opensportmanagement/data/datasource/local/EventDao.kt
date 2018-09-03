package com.zcorp.opensportmanagement.data.datasource.local

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface EventDao {
    @Insert(onConflict = REPLACE)
    fun saveEvents(entities: List<EventEntity>)

    @Insert(onConflict = REPLACE)
    fun saveEvent(entity: EventEntity)

    @Query("SELECT * FROM event WHERE team_id = :teamId ORDER BY from_date ASC")
    fun loadEvents(teamId: Int): DataSource.Factory<Int, EventEntity>
}