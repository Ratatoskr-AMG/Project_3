package ru.ratatoskr.project_3.domain.useCases.sqlite

import ru.ratatoskr.project_3.data.storage.HeroesDao
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesOpendotaRepoImpl
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesSqliteRepoImpl
import javax.inject.Inject

class GetHeroByIdUseCase @Inject constructor(val localRepoImpl: HeroesSqliteRepoImpl) {
    suspend fun GetHeroById(id: String): Hero {
        return localRepoImpl.getHeroById(id)
    }
}


