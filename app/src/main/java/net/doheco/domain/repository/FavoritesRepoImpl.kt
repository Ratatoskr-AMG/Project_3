package net.doheco.domain.repository

import android.util.Log
import net.doheco.data.dao.RoomAppDatabase
import net.doheco.domain.model.Favorites
import java.lang.Exception
import javax.inject.Inject

class FavoritesRepoImpl @Inject constructor(
    private val roomAppDatabase: RoomAppDatabase,
) {

    suspend fun dropHeroFromFavorites(heroId: Int){
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

    fun getAllFavorites(): List<Favorites> {

        return roomAppDatabase.favoritesDao().all

    }

    suspend fun getIfHeroIsFavoriteById(heroId: Int): Boolean {

        return roomAppDatabase.favoritesDao().fetchHeroId(heroId)!=null

    }
}