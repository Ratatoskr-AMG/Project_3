package ru.ratatoskr.doheco.domain.useCases.user

import android.content.SharedPreferences
import ru.ratatoskr.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class GetPlayerIdFromSP @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {

    fun getPlayerIdFromSP(sp: SharedPreferences): String {
        return appUserRepoImpl.getPlayerIdFromSP(sp)
    }

}