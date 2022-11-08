package net.doheco.domain.useCases.user

import android.content.SharedPreferences
import net.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class GetPlayerTierFromSPUseCase @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {

    fun getPlayerTierFromSP(sp: SharedPreferences): String {
        return appUserRepoImpl.getPlayerTierFromSP(sp)
    }

}