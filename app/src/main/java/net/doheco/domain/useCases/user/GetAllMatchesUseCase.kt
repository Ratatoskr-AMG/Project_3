package net.doheco.domain.useCases.user

import net.doheco.domain.model.opendota.OpenDotaMatch
import net.doheco.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class GetAllMatchesUseCase @Inject constructor(
    private val heroesRepoImpl: HeroesRepoImpl
) {

    suspend fun getMatches(steamId: String): List<OpenDotaMatch> {
        return heroesRepoImpl.getMatchesFormApi(steamId)
    }
}