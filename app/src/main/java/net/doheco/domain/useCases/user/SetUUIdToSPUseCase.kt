package net.doheco.domain.useCases.user

import android.content.SharedPreferences
import android.util.Log
import net.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class SetUUIdToSPUseCase @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {

    fun SetUUIdToSP(sp: SharedPreferences,UUId:String) {
        appUserRepoImpl.setUUIdToSP(sp,UUId)
    }

}