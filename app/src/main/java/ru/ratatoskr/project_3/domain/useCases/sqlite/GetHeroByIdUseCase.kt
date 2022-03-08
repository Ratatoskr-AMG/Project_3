package ru.ratatoskr.project_3.domain.useCases.sqlite

import ru.ratatoskr.project_3.data.storage.HeroesDao
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesSqliteRepoImpl

class GetHeroByIdUseCase(private val heroesRepository: HeroesSqliteRepoImpl) {
    suspend fun GetHeroById(id:String): Hero{
        return heroesRepository.getHeroById(id)
    }
}


