package ru.ratatoskr.project_3.domain.useCases.user

import android.content.SharedPreferences
import android.util.Log
import ru.ratatoskr.project_3.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class SetPlayerTierToSPUseCase @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {

    fun setPlayerTierToSP(sp: SharedPreferences,tier:String) {
        appUserRepoImpl.setPlayerTierToSP(sp,tier)
        Log.d("TOHA2","setPlayerTierToSP:"+tier);
    }

}