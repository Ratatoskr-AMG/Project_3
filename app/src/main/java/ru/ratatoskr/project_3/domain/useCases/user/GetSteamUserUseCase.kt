package ru.ratatoskr.project_3.domain.useCases.user

import ru.ratatoskr.project_3.domain.model.steam.SteamResponse
import ru.ratatoskr.project_3.domain.repository.AppUserRepoImpl
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

