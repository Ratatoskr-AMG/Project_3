package ru.ratatoskr.project_3.domain.useCases.user

import android.content.SharedPreferences
import ru.ratatoskr.project_3.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class GetPlayerSteamNameFromSPUseCase @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {

    fun getPlayerSteamNameFromSP(sp: SharedPreferences): String {
        return appUserRepoImpl.getPlayerSteamNameFromSP(sp)
    }

}