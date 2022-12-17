package net.doheco.domain.useCases.user

import android.content.SharedPreferences
import net.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class GetPlayerIdFromSP @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {

    fun getPlayerIdFromSP(sp: SharedPreferences): String {
        return appUserRepoImpl._old_getPlayerIdFromSP(sp)
    }

}