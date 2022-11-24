package net.doheco.domain.repository

import android.util.Log
import net.doheco.data.dao.FavoritesDao
import net.doheco.domain.model.Favorites
import java.lang.Exception
import javax.inject.Inject

class FavoritesRepoImpl @Inject constructor(
    private val dao: FavoritesDao,
) {
    suspend fun dropHeroFromFavorites(heroId: Int){
        dao.dropFavorite(heroId)
    }
    suspend fun addHeroToFavoritesById(heroId: Int) {
        try {
            Log.e("TOHA", "addHeroToFavoritesById " + heroId)
            dao.insertFavorites(Favorites(null,heroId))
        } catch (e: Exception) {
            Log.e(
                "TOHA",
                "addHeroToFavoritesById " + heroId + " e: " + e.message.toString()
            )
        }
    }
    fun getAllFavorites(): List<Favorites> {
        return dao.all
    }

    suspend fun getIfHeroIsFavoriteById(heroId: Int): Boolean {
        return dao.fetchHeroId(heroId)!=null
    }
}