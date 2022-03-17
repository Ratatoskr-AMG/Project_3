package ru.ratatoskr.project_3.domain.useCases.sqlite.user

import ru.ratatoskr.project_3.domain.model.SteamPlayer
import ru.ratatoskr.project_3.domain.repository.user.AppUserRepoImpl
import javax.inject.Inject

class AddSteamPlayerUseCase @Inject constructor(private val userSteamRepo: AppUserRepoImpl) {

    suspend fun addPlayer(player: SteamPlayer){
        return userSteamRepo.addPlayer(player)
    }

}