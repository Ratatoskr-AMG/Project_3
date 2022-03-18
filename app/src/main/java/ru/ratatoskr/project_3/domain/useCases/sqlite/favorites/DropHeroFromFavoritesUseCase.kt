package ru.ratatoskr.project_3.domain.useCases.sqlite.favorites

import ru.ratatoskr.project_3.domain.repository.favorites.FavoritesSqliteRepoImpl
import javax.inject.Inject

class  DropHeroFromFavoritesUseCase @Inject constructor(val localRepoImpl: FavoritesSqliteRepoImpl){

    suspend fun dropHeroFromFavorites(id: Int) {
        return localRepoImpl.dropHeroFromFavorites(id)
    }
}