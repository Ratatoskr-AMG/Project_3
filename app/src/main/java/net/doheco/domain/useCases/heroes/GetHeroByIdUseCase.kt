package net.doheco.domain.useCases.heroes

import net.doheco.domain.model.Hero
import net.doheco.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class GetHeroByIdUseCase @Inject constructor(val localRepoImpl: HeroesRepoImpl) {
    suspend fun GetHeroById(id: String): Hero {
        return localRepoImpl.getHeroById(id)
    }
}


