package ru.ratatoskr.project_3.domain.useCases.steam

import ru.ratatoskr.project_3.domain.model.SteamPlayer
import ru.ratatoskr.project_3.domain.model.SteamResponse
import ru.ratatoskr.project_3.domain.repository.user.UserSteamRepoImpl
import javax.inject.Inject


class GetSteamUserUseCase @Inject constructor(
    var steamRepoImpl: UserSteamRepoImpl,
) {

    suspend fun getSteamResponseOnId(id: String): SteamResponse {
        return steamRepoImpl.getResponseFromSteam(id)
    }

    suspend fun getSteamPlayer(): SteamPlayer {
        return steamRepoImpl.getSteamUser()
    }
}