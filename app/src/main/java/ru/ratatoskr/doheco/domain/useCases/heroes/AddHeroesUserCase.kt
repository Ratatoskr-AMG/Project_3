package ru.ratatoskr.doheco.domain.useCases.heroes

import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class AddHeroesUserCase @Inject constructor(private val localRepoImpl: HeroesRepoImpl) {

    suspend fun addHeroes(heroes: List<Hero>){
        return localRepoImpl.updateHeroesList(heroes)
    }

}