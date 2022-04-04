package ru.ratatoskr.project_3.domain.useCases.heroes

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class AddHeroesUserCase @Inject constructor(private val localRepoImpl: HeroesRepoImpl) {

    suspend fun addHeroes(heroes: List<Hero>){
        return localRepoImpl.updateHeroesList(heroes)
    }

}