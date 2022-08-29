package ru.ratatoskr.doheco.domain.useCases.user

import ru.ratatoskr.doheco.domain.model.steam.SteamResponse
import ru.ratatoskr.doheco.domain.repository.AppUserRepoImpl
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

