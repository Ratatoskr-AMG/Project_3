package net.doheco.domain.useCases.favorites

import net.doheco.domain.repository.FavoritesRepoImpl
import javax.inject.Inject

class GetIfHeroIsFavoriteUseCase @Inject constructor(val localRepoImpl: FavoritesRepoImpl){

    suspend fun getIfHeroIsFavoriteById(id: Int): Boolean {
        return localRepoImpl.getIfHeroIsFavoriteById(id)
    }
}