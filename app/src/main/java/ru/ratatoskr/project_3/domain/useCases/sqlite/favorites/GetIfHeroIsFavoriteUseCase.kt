package ru.ratatoskr.project_3.domain.useCases.sqlite.favorites

import ru.ratatoskr.project_3.domain.repository.favorites.FavoritesSqliteRepoImpl
import javax.inject.Inject

class GetIfHeroIsFavoriteUseCase @Inject constructor(val localRepoImpl: FavoritesSqliteRepoImpl){

    suspend fun getIfHeroIsFavoriteById(id: Int): Boolean {
        return localRepoImpl.getIfHeroIsFavoriteById(id)
    }
}