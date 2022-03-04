package ru.ratatoskr.project_3.domain.useCases

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.HeroesRepoImpl

class GetAllHeroesFromApiUseCase(private val HeroesRepository: HeroesRepoImpl) {
    suspend fun getAllHeroesFromApi(): List<Hero>{
        val heroes = HeroesRepository.getAllHeroesListFromAPI()
        HeroesRepository.updateAllHeroesTable(heroes)
        return heroes
    }
}