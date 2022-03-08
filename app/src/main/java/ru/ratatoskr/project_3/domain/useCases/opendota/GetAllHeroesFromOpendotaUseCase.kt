package ru.ratatoskr.project_3.domain.useCases.opendota

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesOpendotaRepoImpl

class GetAllHeroesFromOpendotaUseCase(
    private val heroesRepository: HeroesOpendotaRepoImpl
    ) {
    suspend fun getAllHeroesFromApi(): List<Hero>{
        val heroes = heroesRepository.getAllHeroesListFromAPI()

        return heroes
    }
}