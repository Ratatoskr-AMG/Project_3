package ru.ratatoskr.project_3.domain.useCases.favorites

import ru.ratatoskr.project_3.domain.repository.favorites.FavoritesRepoImpl
import javax.inject.Inject

class GetIfHeroIsFavoriteUseCase @Inject constructor(val localRepoImpl: FavoritesRepoImpl){

    suspend fun getIfHeroIsFavoriteById(id: Int): Boolean {
        return localRepoImpl.getIfHeroIsFavoriteById(id)
    }
}