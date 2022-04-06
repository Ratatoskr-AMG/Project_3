package ru.ratatoskr.project_3.domain.useCases.user

import ru.ratatoskr.project_3.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class GetDotaBuffUserUseCase @Inject constructor(
    var steamRepoImpl: AppUserRepoImpl,
) {

    suspend fun getDotabuffResponseOnId(steam_user_id: String): String {
        return steamRepoImpl. getResponseFromDotaBuff(steam_user_id)
    }

}