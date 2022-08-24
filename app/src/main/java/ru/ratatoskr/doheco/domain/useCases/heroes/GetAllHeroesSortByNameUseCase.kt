package ru.ratatoskr.doheco.domain.useCases.heroes

import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class GetAllHeroesSortByNameUseCase @Inject constructor(
    val localRepoImpl: HeroesRepoImpl
) {
    fun getAllHeroesSortByName(): List<Hero> {
        return localRepoImpl.getAllHeroesList()
    }
    suspend fun getAllHeroesByStrSortByName(str:String): List<Hero> {
        return localRepoImpl.getAllHeroesListByStr(str)
    }
}