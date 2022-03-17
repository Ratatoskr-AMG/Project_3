package ru.ratatoskr.project_3.data.storage

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ratatoskr.project_3.data.contracts.FavoritesContract
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.data.contracts.SteamContract
import ru.ratatoskr.project_3.domain.model.Favorites
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.model.SteamPlayer

@Dao
interface SteamUsersDao {
    @Insert(entity = SteamPlayer::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: SteamPlayer)

    @get:Query("SELECT * FROM ${SteamContract.USERS_STEAM_TABLE_NAME}")
    val player: SteamPlayer
}