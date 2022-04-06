package ru.ratatoskr.project_3.domain.useCases.favorites

import ru.ratatoskr.project_3.domain.repository.FavoritesRepoImpl
import javax.inject.Inject

class InsertHeroToFavoritesUseCase @Inject constructor(private val localRepoImpl: FavoritesRepoImpl) {

    suspend fun insertHeroToFavorites(heroId:Int) {
        return localRepoImpl.addHeroToFavoritesById(heroId)
    }

}