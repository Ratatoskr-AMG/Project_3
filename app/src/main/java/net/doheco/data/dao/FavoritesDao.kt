package net.doheco.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.doheco.domain.model.Favorites

@Dao
interface FavoritesDao {

    @get:Query("SELECT * FROM ${net.doheco.data.contracts.FavoritesContract.FAVORITES_TABLE_NAME}")
    val all: List<Favorites>

    @Insert(entity = Favorites::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(favorites: Favorites)

    @Query("SELECT * FROM ${net.doheco.data.contracts.FavoritesContract.FAVORITES_TABLE_NAME} WHERE ${net.doheco.data.contracts.FavoritesContract.COLUMN_HERO_ID} = :heroId")
    suspend fun fetchHeroId(heroId: Int): Favorites

    @Query("DELETE FROM ${net.doheco.data.contracts.FavoritesContract.FAVORITES_TABLE_NAME} WHERE ${net.doheco.data.contracts.FavoritesContract.COLUMN_HERO_ID} = :heroId")
    suspend fun dropFavorite(heroId: Int)

}