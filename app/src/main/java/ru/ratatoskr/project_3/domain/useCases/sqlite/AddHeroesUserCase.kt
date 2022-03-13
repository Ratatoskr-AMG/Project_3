package ru.ratatoskr.project_3.domain.useCases.sqlite

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesSqliteRepoImpl
import javax.inject.Inject

class AddHeroesUserCase @Inject constructor(private val localRepoImpl: HeroesSqliteRepoImpl) {

    suspend fun addHeroes(heroes: List<Hero>){
        return localRepoImpl.updateHeroesList(heroes)
    }

}