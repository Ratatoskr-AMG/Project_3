package net.doheco.domain.useCases.heroes

import net.doheco.domain.model.Hero
import net.doheco.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class AddHeroesUserCase @Inject constructor(private val localRepoImpl: HeroesRepoImpl) {

    suspend fun addHeroes(heroes: List<Hero>){
        return localRepoImpl.updateHeroesList(heroes)
    }

}