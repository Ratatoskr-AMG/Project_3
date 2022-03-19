package ru.ratatoskr.project_3.domain.useCases.favorites

import ru.ratatoskr.project_3.domain.repository.favorites.FavoritesRepoImpl
import javax.inject.Inject

class  DropHeroFromFavoritesUseCase @Inject constructor(val localRepoImpl: FavoritesRepoImpl){

    suspend fun dropHeroFromFavorites(id: Int) {
        return localRepoImpl.dropHeroFromFavorites(id)
    }
}