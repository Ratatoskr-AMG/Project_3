package ru.ratatoskr.doheco.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ratatoskr.doheco.data.contracts.SteamContract
import ru.ratatoskr.doheco.domain.model.steam.SteamPlayer

@Dao
interface SteamUsersDao {
    @Insert(entity = SteamPlayer::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: SteamPlayer)

    @get:Query("SELECT * FROM ${ru.ratatoskr.doheco.data.contracts.SteamContract.USERS_STEAM_TABLE_NAME}")
    val player: SteamPlayer
}