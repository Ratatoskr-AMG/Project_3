package ru.ratatoskr.project_3.domain.useCases.heroes

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class GetHeroByIdUseCase @Inject constructor(val localRepoImpl: HeroesRepoImpl) {
    suspend fun GetHeroById(id: String): Hero {
        return localRepoImpl.getHeroById(id)
    }
}


