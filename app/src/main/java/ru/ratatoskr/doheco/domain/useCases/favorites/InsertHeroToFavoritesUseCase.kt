package ru.ratatoskr.doheco.domain.useCases.favorites

import ru.ratatoskr.doheco.domain.repository.FavoritesRepoImpl
import javax.inject.Inject

class InsertHeroToFavoritesUseCase @Inject constructor(private val localRepoImpl: FavoritesRepoImpl) {

    suspend fun insertHeroToFavorites(heroId:Int) {
        return localRepoImpl.addHeroToFavoritesById(heroId)
    }

}