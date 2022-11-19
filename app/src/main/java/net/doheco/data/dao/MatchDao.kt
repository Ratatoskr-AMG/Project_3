package net.doheco.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.doheco.domain.model.DotaMatch

@Dao
interface MatchDao {

    @Query("SELECT * FROM matches")
    suspend fun getAll(): List<DotaMatch>

    @Query("SELECT * FROM matches WHERE matchId IN (:id)")
    suspend fun getFromId(id: Long): DotaMatch

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(match: List<DotaMatch>)

    @Query("DELETE FROM matches")
    suspend fun deleteAll()
}
