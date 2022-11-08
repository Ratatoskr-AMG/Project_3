package net.doheco.domain.useCases.user

import net.doheco.domain.model.steam.SteamResponse
import net.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject


class GetSteamUserUseCase @Inject constructor(
    var steamRepoImpl: AppUserRepoImpl,
) {

    suspend fun getSteamResponseOnId(id: String): SteamResponse {
        return steamRepoImpl. getResponseFromSteam(id)
    }

    /*
    suspend fun getSteamPlayer(): SteamPlayer {
        return steamRepoImpl.getSteamUser()
    }
    */
}

