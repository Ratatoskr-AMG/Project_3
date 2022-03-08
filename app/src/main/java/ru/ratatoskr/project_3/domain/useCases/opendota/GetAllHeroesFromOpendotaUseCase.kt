package ru.ratatoskr.project_3.domain.useCases.opendota

import dagger.hilt.android.AndroidEntryPoint
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesOpendotaRepoImpl
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesSqliteRepoImpl
import javax.inject.Inject


class GetAllHeroesFromOpendotaUseCase @Inject constructor(
    var opendotaRepoImpl: HeroesOpendotaRepoImpl,
)
    {
    suspend fun getAllHeroesFromApi(): List<Hero>{
        return opendotaRepoImpl.getAllHeroesListFromAPI()
    }
}