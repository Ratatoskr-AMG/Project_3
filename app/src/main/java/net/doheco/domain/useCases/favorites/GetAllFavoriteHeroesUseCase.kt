package net.doheco.domain.useCases.favorites;

import net.doheco.domain.model.Hero
import javax.inject.Inject;
import net.doheco.domain.repository.FavoritesRepoImpl;
import net.doheco.domain.repository.HeroesRepoImpl
import net.doheco.domain.useCases.heroes.GetHeroByIdUseCase

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