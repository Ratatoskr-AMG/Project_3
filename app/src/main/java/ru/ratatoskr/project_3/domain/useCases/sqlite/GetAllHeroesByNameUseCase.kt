package ru.ratatoskr.project_3.domain.useCases.sqlite

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesOpendotaRepoImpl
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesSqliteRepoImpl
import javax.inject.Inject

class GetAllHeroesByNameUseCase @Inject constructor(
    val localRepoImpl: HeroesSqliteRepoImpl
) {
    fun getAllHeroesByName(): List<Hero> {
        return localRepoImpl.getAllHeroesListFromDB()
    }

}