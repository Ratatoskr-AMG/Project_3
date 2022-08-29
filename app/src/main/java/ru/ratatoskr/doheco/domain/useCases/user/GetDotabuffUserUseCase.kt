package ru.ratatoskr.doheco.domain.useCases.user

import ru.ratatoskr.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class GetDotaBuffUserUseCase @Inject constructor(
    var steamRepoImpl: AppUserRepoImpl,
) {

    suspend fun getDotabuffResponseOnId(steam_user_id: String): String {
        return steamRepoImpl. getResponseFromDotaBuff(steam_user_id)
    }

}