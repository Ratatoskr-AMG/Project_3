package ru.ratatoskr.doheco.data.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ratatoskr.doheco.data.contracts.FavoritesContract
import ru.ratatoskr.doheco.data.contracts.HeroesContract
import ru.ratatoskr.doheco.domain.model.Favorites
import ru.ratatoskr.doheco.domain.model.Hero

@Dao
interface FavoritesDao {

    @get:Query("SELECT * FROM ${ru.ratatoskr.doheco.data.contracts.FavoritesContract.FAVORITES_TABLE_NAME}")
    val all: List<Favorites>

    @Insert(entity = Favorites::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(favorites: Favorites)

    @Query("SELECT * FROM ${ru.ratatoskr.doheco.data.contracts.FavoritesContract.FAVORITES_TABLE_NAME} WHERE ${ru.ratatoskr.doheco.data.contracts.FavoritesContract.COLUMN_HERO_ID} = :heroId")
    suspend fun fetchHeroId(heroId: Int): Favorites

    @Query("DELETE FROM ${ru.ratatoskr.doheco.data.contracts.FavoritesContract.FAVORITES_TABLE_NAME} WHERE ${ru.ratatoskr.doheco.data.contracts.FavoritesContract.COLUMN_HERO_ID} = :heroId")
    suspend fun dropFavorite(heroId: Int)

}