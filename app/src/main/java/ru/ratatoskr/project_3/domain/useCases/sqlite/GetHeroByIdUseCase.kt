package ru.ratatoskr.project_3.domain.useCases.sqlite

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesLocalRepoImpl

class GetHeroByIdUseCase(private val heroesRepository: HeroesLocalRepoImpl) {
    suspend fun GetHeroById(id:String): Hero{
        return heroesRepository.getHeroById(id)
    }
}