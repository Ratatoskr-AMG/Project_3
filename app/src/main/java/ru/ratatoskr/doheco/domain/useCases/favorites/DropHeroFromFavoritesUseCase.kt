package ru.ratatoskr.doheco.domain.useCases.favorites

import ru.ratatoskr.doheco.domain.repository.FavoritesRepoImpl
import javax.inject.Inject

class  DropHeroFromFavoritesUseCase @Inject constructor(val localRepoImpl: FavoritesRepoImpl){

    suspend fun dropHeroFromFavorites(id: Int) {
        return localRepoImpl.dropHeroFromFavorites(id)
    }
}