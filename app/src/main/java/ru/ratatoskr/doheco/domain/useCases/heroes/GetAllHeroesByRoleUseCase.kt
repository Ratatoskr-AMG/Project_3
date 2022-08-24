package ru.ratatoskr.doheco.domain.useCases.heroes

import ru.ratatoskr.doheco.domain.extensions.toArrayList
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class GetAllHeroesByRoleUseCase @Inject constructor(val localRepoImpl: HeroesRepoImpl) {

    fun getAllHeroesByRole(role:String) : List<Hero>{
        var heroes = localRepoImpl.getAllHeroesList().toArrayList()

        return heroes.filter{
            it.roles[0].contains(role)
        }
    }
}