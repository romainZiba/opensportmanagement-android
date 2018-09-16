package com.zcorp.opensportmanagement.data.datasource.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveTeams(entities: List<TeamEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveTeam(entity: TeamEntity)

    @Query("SELECT * FROM team")
    abstract fun loadTeams(): Flowable<List<TeamEntity>>

    @Query("SELECT * FROM members m WHERE m.team_id = :teamId")
    abstract fun loadTeamMembers(teamId: Int): Flowable<List<TeamMemberEntity>>

    /**
     * We return a list instead of a single element because of this:
     * https://github.com/googlesamples/android-architecture-components/issues/84
     */
    @Query("SELECT * FROM members m WHERE m._id = :teamMemberId LIMIT 1")
    abstract fun loadTeamMember(teamMemberId: Int): Flowable<List<TeamMemberEntity>>

    /**
     * https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1
     */
    fun getDistincMemberById(id: Int): Flowable<List<TeamMemberEntity>> = loadTeamMember(id).distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveTeamMembers(entities: List<TeamMemberEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveTeamMember(entity: TeamMemberEntity)

    @Update
    abstract fun updateTeamMember(entity: TeamMemberEntity)
}