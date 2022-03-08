package ru.ratatoskr.project_3.domain.useCases.sqlite

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesLocalRepoImpl
import javax.inject.Inject

class GetAllHeroesByNameUseCase @Inject constructor(
    val localRepoImpl: HeroesLocalRepoImpl
) {
    fun getAllHeroesByName(): List<Hero> {
        return localRepoImpl.getAllHeroesListFromDB()
    }
}