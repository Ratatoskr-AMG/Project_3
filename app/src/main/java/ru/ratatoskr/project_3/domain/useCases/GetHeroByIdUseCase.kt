package ru.ratatoskr.project_3.domain.useCases

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.HeroesRepoImpl

class GetHeroByIdUseCase(private val HeroesRepository: HeroesRepoImpl) {
    suspend fun GetHeroById(id:String): Hero{
        return HeroesRepository.getHeroById(id)
    }
}