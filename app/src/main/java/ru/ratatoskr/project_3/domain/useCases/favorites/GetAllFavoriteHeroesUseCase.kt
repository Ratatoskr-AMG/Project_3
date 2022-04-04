package ru.ratatoskr.project_3.domain.useCases.favorites;

import ru.ratatoskr.project_3.domain.model.Hero
import javax.inject.Inject;
import ru.ratatoskr.project_3.domain.repository.FavoritesRepoImpl;
import ru.ratatoskr.project_3.domain.repository.HeroesRepoImpl
import ru.ratatoskr.project_3.domain.useCases.heroes.GetHeroByIdUseCase

class GetAllFavoriteHeroesUseCase @Inject constructor(
    val favoritesLocalRepoImpl: FavoritesRepoImpl,
    val heroesLocalRepoImpl: HeroesRepoImpl
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