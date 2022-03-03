package ru.ratatoskr.project_3.domain.useCases

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.HeroesRepoImpl

class GetAllHeroesByNameUseCase (private val HeroesRepository: HeroesRepoImpl) {
    fun getAllHeroesByName(): List<Hero>{
        return HeroesRepository.getAllHeroesListFromDB()
    }
}