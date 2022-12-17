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

    suspend fun updateMatchesDb(list: List<DotaMatch>) {
        heroesRepoImpl.deleteAllMatchesFromDb()
        heroesRepoImpl.addToDb(list)
    }

    suspend fun getMatchesFromDb(): List<DotaMatch> {
        return  heroesRepoImpl.getFromDb().asReversed()
    }


}