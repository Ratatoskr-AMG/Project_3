package net.doheco.domain.useCases.user

import android.content.SharedPreferences
import net.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class GetHeroesBaseLastModifiedFromSPUseCase @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {

    fun getHeroesBaseLastModifiedFromSPUseCase(sp: SharedPreferences): String {
        return appUserRepoImpl.getHeroesBaseLastModifiedFromSPUseCase(sp)
    }

}