package ru.ratatoskr.project_3.domain.useCases.sqlite.favorites

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.favorites.FavoritesSqliteRepoImpl
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesSqliteRepoImpl
import javax.inject.Inject

class InsertHeroToFavoritesUseCase @Inject constructor(private val localRepoImpl: FavoritesSqliteRepoImpl) {

    suspend fun insertHeroToFavorites(heroId:Int) {
        return localRepoImpl.addHeroToFavoritesById(heroId)
    }

}