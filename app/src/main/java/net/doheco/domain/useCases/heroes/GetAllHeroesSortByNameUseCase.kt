package net.doheco.domain.useCases.heroes

import net.doheco.domain.model.Hero
import net.doheco.domain.repository.HeroesRepoImpl
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