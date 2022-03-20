package ru.ratatoskr.project_3.domain.useCases.heroes

import android.util.Log
import ru.ratatoskr.project_3.domain.extensions.toArrayList
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesRepoImpl
import javax.inject.Inject

class GetAllHeroesByRoleUseCase @Inject constructor(val localRepoImpl: HeroesRepoImpl) {

    fun getAllHeroesByRole(role:String) : List<Hero>{
        var heroes = localRepoImpl.getAllHeroesList().toArrayList()

        return heroes.filter{
            it.roles[0].contains(role)
        }
    }
}