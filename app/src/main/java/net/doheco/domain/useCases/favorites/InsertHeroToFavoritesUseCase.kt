package net.doheco.domain.useCases.favorites

import net.doheco.domain.repository.FavoritesRepoImpl
import javax.inject.Inject

class InsertHeroToFavoritesUseCase @Inject constructor(private val localRepoImpl: FavoritesRepoImpl) {

    suspend fun insertHeroToFavorites(heroId:Int) {
        return localRepoImpl.addHeroToFavoritesById(heroId)
    }

}