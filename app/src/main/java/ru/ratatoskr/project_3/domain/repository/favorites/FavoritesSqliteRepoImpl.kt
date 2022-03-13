package ru.ratatoskr.project_3.domain.repository.favorites

import android.util.Log
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.Favorites
import java.lang.Exception
import javax.inject.Inject

class FavoritesSqliteRepoImpl @Inject constructor(
    private val roomAppDatabase: RoomAppDatabase,
) {

    suspend fun DropHeroFromFavorites(heroId: Int){

        roomAppDatabase.favoritesDao().dropFavorite(heroId!!)
    }

    suspend fun addHeroToFavoritesById(heroId: Int) {
        try {
            Log.e("TOHA", "addHeroToFavoritesById " + heroId)
            roomAppDatabase.favoritesDao().insertFavorites(Favorites(null,heroId))

        } catch (e: Exception) {
            Log.e(
                "TOHA",
                "addHeroToFavoritesById " + heroId + " e: " + e.message.toString()
            )
        }
    }

    suspend fun getAllFavorites(): List<Favorites> {

        return roomAppDatabase.favoritesDao().all

    }

    suspend fun getIfHeroIsFavoriteById(heroId: Int): Boolean {

        return roomAppDatabase.favoritesDao().fetchHeroId(heroId)!=null

    }
}