package ru.ratatoskr.project_3.data

import ru.ratatoskr.project_3.domain.model.Hero

interface HeroesRepo {
    suspend fun getAllHeroesList(): List<Hero>
}