package ru.ratatoskr.project_3.domain.useCases.user

import ru.ratatoskr.project_3.domain.model.SteamPlayer
import ru.ratatoskr.project_3.domain.model.SteamResponse
import ru.ratatoskr.project_3.domain.repository.user.AppUserRepoImpl
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