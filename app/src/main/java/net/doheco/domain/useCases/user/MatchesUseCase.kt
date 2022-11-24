package net.doheco.domain.useCases.user

import net.doheco.data.converters.DotaMatchesConverter
import net.doheco.domain.model.DotaMatch
import net.doheco.domain.repository.HeroesRepoImpl
import net.doheco.domain.useCases.heroes.GetHeroByIdUseCase
import javax.inject.Inject

class MatchesUseCase @Inject constructor(
    private val heroesRepoImpl: HeroesRepoImpl
) {
    suspend fun getMatches(steamId: String,usecase:GetHeroByIdUseCase): List<DotaMatch> {
        val newList = heroesRepoImpl.getMatchesFormApi(steamId)

        return newList.map {
            var hero = usecase.GetHeroById(it.heroId.toString())
            DotaMatchesConverter.doForward(it,hero)
        }
    }

    suspend fun updateFromDb(list: List<DotaMatch>) {
        heroesRepoImpl.deleteAllMatchesFromDb()
        heroesRepoImpl.addToDb(list)
    }

    suspend fun getFromDb(): List<DotaMatch> {
        return  heroesRepoImpl.getFromDb().asReversed()
    }

    suspend fun getMatchesFormId(id: Long): DotaMatch {
        return heroesRepoImpl.getFromDbInId(id)
    }

}