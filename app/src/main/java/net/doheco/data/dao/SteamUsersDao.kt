package net.doheco.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.doheco.domain.model.steam.SteamPlayer

@Dao
interface SteamUsersDao {
    @Insert(entity = SteamPlayer::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: SteamPlayer)

    @get:Query("SELECT * FROM ${net.doheco.data.contracts.SteamContract.USERS_STEAM_TABLE_NAME}")
    val player: SteamPlayer
}