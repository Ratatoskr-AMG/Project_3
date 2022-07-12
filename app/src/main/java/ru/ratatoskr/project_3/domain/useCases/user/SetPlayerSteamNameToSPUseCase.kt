package ru.ratatoskr.project_3.domain.useCases.user

import android.content.SharedPreferences
import android.util.Log
import ru.ratatoskr.project_3.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class SetPlayerSteamNameToSPUseCase @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {

    fun setPlayerSteamNameToSP(sp: SharedPreferences,name:String) {
        appUserRepoImpl.setPlayerSteamNameToSP(sp,name)
        Log.d("TOHA2","setPlayerSteamNameToSP:"+name);
    }

}