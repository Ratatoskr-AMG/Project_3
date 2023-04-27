package net.doheco.domain.useCases.user

import android.content.SharedPreferences
import net.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class UUIdSPUseCase @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {
    fun SetAppUUIdToSP(sp: SharedPreferences, UUId:String) {
        appUserRepoImpl.setAppUUIdToSP(sp,UUId)
    }

    fun GetAppUUIdFromSP(sp: SharedPreferences) : String {
        return appUserRepoImpl.getAppUUIdFromSP(sp)
    }

    fun SetSteamUUIdToSP(sp: SharedPreferences, UUId:String) {
        appUserRepoImpl.setSteamUUIdToSP(sp,UUId)
    }

    public fun GetSteamUUIdFromSP(sp: SharedPreferences) : String {
        return appUserRepoImpl.getSteamUUIdFromSP(sp)
    }

}