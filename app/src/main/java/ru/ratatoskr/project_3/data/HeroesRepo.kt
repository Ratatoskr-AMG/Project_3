package ru.ratatoskr.project_3.data

import android.content.Context
import ru.ratatoskr.project_3.domain.model.Hero

interface HeroesRepo {
    suspend fun getAllHeroesListFromAPI(): List<Hero>
    suspend fun getAllHeroesListFromDB(context:Context): List<Hero>
}