package net.doheco.domain.useCases.user

import android.content.SharedPreferences
import android.util.Log
import net.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class SetPlayerSteamNameToSPUseCase @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {

    fun setPlayerSteamNameToSP(sp: SharedPreferences,name:String) {
        appUserRepoImpl.setPlayerSteamNameToSP(sp,name)
        Log.d("TOHA2","setPlayerSteamNameToSP:"+name);
    }

}