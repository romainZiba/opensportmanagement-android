package com.zcorp.opensportmanagement.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTeams(entities: List<TeamEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTeam(entity: TeamEntity)

    @Query("SELECT * FROM team")
    fun loadTeams(): Single<List<TeamEntity>>
}