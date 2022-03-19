package ru.ratatoskr.project_3.domain.useCases.heroes

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesRepoImpl
import javax.inject.Inject

class GetAllHeroesByNameUseCase @Inject constructor(
    val localRepoImpl: HeroesRepoImpl
) {
    fun getAllHeroesByName(): List<Hero> {
        return localRepoImpl.getAllHeroesList()
    }

}