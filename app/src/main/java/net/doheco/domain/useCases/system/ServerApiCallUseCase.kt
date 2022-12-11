package net.doheco.domain.useCases.system

import net.doheco.domain.model.Hero
import net.doheco.domain.model.HeroesAndMatchesApiCallResult
import net.doheco.domain.repository.DohecoServerRepoImpl
import net.doheco.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class ServerApiCallUseCase @Inject constructor(
    var dohecoServerRepoImpl: DohecoServerRepoImpl,
){

    suspend fun getHeroesAndMatches(steamId:String): HeroesAndMatchesApiCallResult {
        return dohecoServerRepoImpl.getHeroesAndMatches(steamId)
    }

}
