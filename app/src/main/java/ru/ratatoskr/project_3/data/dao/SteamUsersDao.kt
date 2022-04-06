package ru.ratatoskr.project_3.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ratatoskr.project_3.data.contracts.SteamContract
import ru.ratatoskr.project_3.domain.model.steam.SteamPlayer

@Dao
interface SteamUsersDao {
    @Insert(entity = SteamPlayer::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: SteamPlayer)

    @get:Query("SELECT * FROM ${SteamContract.USERS_STEAM_TABLE_NAME}")
    val player: SteamPlayer
}