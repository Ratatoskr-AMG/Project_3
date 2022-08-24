package ru.ratatoskr.doheco.domain.useCases.heroes

import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class GetHeroByIdUseCase @Inject constructor(val localRepoImpl: HeroesRepoImpl) {
    suspend fun GetHeroById(id: String): Hero {
        return localRepoImpl.getHeroById(id)
    }
}


