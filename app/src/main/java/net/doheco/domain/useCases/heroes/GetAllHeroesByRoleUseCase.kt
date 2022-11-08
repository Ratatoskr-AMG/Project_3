package net.doheco.domain.useCases.heroes

import net.doheco.domain.extensions.toArrayList
import net.doheco.domain.model.Hero
import net.doheco.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class GetAllHeroesByRoleUseCase @Inject constructor(val localRepoImpl: HeroesRepoImpl) {

    fun getAllHeroesByRole(role:String) : List<Hero>{
        var heroes = localRepoImpl.getAllHeroesList().toArrayList()

        return heroes.filter{
            it.roles[0].contains(role)
        }
    }
}