package ru.ratatoskr.project_3.data.storage

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ratatoskr.project_3.data.contracts.FavoritesContract
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.domain.model.Favorites
import ru.ratatoskr.project_3.domain.model.Hero

@Dao
interface FavoritesDao {

    @Insert(entity = Favorites::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(heroId: Int)

    @Query("SELECT * FROM ${FavoritesContract.FAVORITES_TABLE_NAME} WHERE ${FavoritesContract.COLUMN_HERO_ID} = :heroId")
    suspend fun fetchHeroId(heroId: Int): Favorites

    @Query("DELETE FROM ${FavoritesContract.FAVORITES_TABLE_NAME} WHERE ${FavoritesContract.COLUMN_HERO_ID} = :heroId")
    suspend fun dropHero(heroId: Int)


}