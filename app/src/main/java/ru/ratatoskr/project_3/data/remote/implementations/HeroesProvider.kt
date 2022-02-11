package ru.ratatoskr.project_3.data.remote.implementations

import ru.ratatoskr.project_3.data.remote.services.HeroesService
import ru.ratatoskr.project_3.domain.model.Hero
import javax.inject.Inject

class HeroesProvider @Inject constructor(
    private val heroesService: HeroesService
) {

    suspend fun fetchHeroes(): List<Hero> {
        return heroesService.getHeroes()
    }


}