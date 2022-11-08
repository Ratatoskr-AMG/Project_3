package net.doheco.domain.useCases.heroes

import android.content.SharedPreferences
import net.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject


class CurrentInfoBlockUseCase @Inject constructor(
    private val appUserRepoImpl: AppUserRepoImpl
) {

    fun getCurrentInfoBlockHero(sp:SharedPreferences): String {
        return appUserRepoImpl.getCurrentInfoBlockHeroPage(sp)
    }

    fun setCurrentInfoBlockHero(sp:SharedPreferences, newInfoBlock:String) {
        appUserRepoImpl.setCurrentInfoBlockHeroPage(sp,newInfoBlock)
    }

    fun getCurrentInfoBlockComparing(sp:SharedPreferences): String {
        return appUserRepoImpl.getCurrentInfoBlockComparingPage(sp)
    }

    fun setCurrentInfoBlockComparing(sp:SharedPreferences, newInfoBlock:String) {
        appUserRepoImpl.setCurrentInfoBlockComparingPage(sp,newInfoBlock)
    }

}