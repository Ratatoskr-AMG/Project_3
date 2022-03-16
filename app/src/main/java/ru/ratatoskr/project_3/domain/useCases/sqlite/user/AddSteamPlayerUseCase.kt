package ru.ratatoskr.project_3.domain.useCases.sqlite.user

import ru.ratatoskr.project_3.domain.model.SteamPlayer
import ru.ratatoskr.project_3.domain.repository.user.UserSteamRepoImpl
import javax.inject.Inject

class AddSteamPlayerUseCase @Inject constructor(private val userSteamRepo: UserSteamRepoImpl) {

    suspend fun addPlayer(player: SteamPlayer){
        return userSteamRepo.addPlayer(player)
    }

}