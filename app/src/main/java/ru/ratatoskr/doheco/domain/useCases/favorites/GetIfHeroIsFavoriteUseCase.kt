package ru.ratatoskr.doheco.domain.useCases.favorites

import ru.ratatoskr.doheco.domain.repository.FavoritesRepoImpl
import javax.inject.Inject

class GetIfHeroIsFavoriteUseCase @Inject constructor(val localRepoImpl: FavoritesRepoImpl){

    suspend fun getIfHeroIsFavoriteById(id: Int): Boolean {
        return localRepoImpl.getIfHeroIsFavoriteById(id)
    }
}