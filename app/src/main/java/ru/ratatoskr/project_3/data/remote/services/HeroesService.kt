package ru.ratatoskr.project_3.data.remote.services

import retrofit2.http.GET
import ru.ratatoskr.project_3.domain.model.Hero

interface HeroesService {

    @GET("./heroes")
    suspend fun getHeroes(): List<Hero>

    @GET("./heroStats")
    suspend fun getHeroesStats(): List<Hero>
}