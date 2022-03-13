package ru.ratatoskr.project_3.domain.repository.favorites

import android.util.Log
import io.ktor.client.request.*
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.useCases.calculations.BasicIndicatorsUseCase
import java.lang.Exception
import javax.inject.Inject

class FavoritesSqliteRepoImpl @Inject constructor(
    private val roomAppDatabase: RoomAppDatabase,
) {

    suspend fun DropHeroFromFavorites(heroId: Int){

        roomAppDatabase.favoritesDao().dropHero(heroId!!)
    }

    suspend fun addHeroToFavoritesById(heroId: Int) {
        try {
            Log.e("TOHA", "addHeroToFavoritesById " + heroId)
            roomAppDatabase.favoritesDao().insertHero(heroId)
        } catch (e: Exception) {
            Log.e(
                "TOHA",
                "addHeroToFavoritesById " + heroId + " e: " + e.message.toString()
            )
        }
    }

    suspend fun getIfHeroIsFavoriteById(heroId: Int): Boolean {

        val favorites = roomAppDatabase.favoritesDao().fetchHeroId(heroId)
        return favorites.heroId > 0

    }
}