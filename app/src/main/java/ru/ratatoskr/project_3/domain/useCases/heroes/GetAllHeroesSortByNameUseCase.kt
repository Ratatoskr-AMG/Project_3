package ru.ratatoskr.project_3.domain.useCases.heroes

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesRepoImpl
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