package ru.ratatoskr.project_3.domain.useCases.sqlite.favorites;

import ru.ratatoskr.project_3.domain.model.Hero
import javax.inject.Inject;
import ru.ratatoskr.project_3.domain.repository.favorites.FavoritesSqliteRepoImpl;
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesSqliteRepoImpl
import ru.ratatoskr.project_3.domain.useCases.sqlite.heroes.GetHeroByIdUseCase

class GetAllFavoriteHeroesUseCase @Inject constructor(
    val favoritesLocalRepoImpl: FavoritesSqliteRepoImpl,
    val heroesLocalRepoImpl: HeroesSqliteRepoImpl
) {
    suspend fun getAllFavoriteHeroesUseCase(): List<Hero> {
        var favorites = favoritesLocalRepoImpl.getAllFavorites()
        var heroes: MutableList<Hero> = mutableListOf()
        for (favorite in favorites) {
            var hero =
                GetHeroByIdUseCase(heroesLocalRepoImpl).GetHeroById(favorite.heroId.toString())
            heroes.add(hero)
        }
        return heroes
    }

}