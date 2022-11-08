package net.doheco.domain.useCases.favorites

import net.doheco.domain.repository.FavoritesRepoImpl
import javax.inject.Inject

class  DropHeroFromFavoritesUseCase @Inject constructor(val localRepoImpl: FavoritesRepoImpl){

    suspend fun dropHeroFromFavorites(id: Int) {
        return localRepoImpl.dropHeroFromFavorites(id)
    }
}