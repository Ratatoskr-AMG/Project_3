package ru.ratatoskr.project_3.domain.useCases.user

import ru.ratatoskr.project_3.domain.model.OpenDotaResponse
import ru.ratatoskr.project_3.domain.repository.AppUserRepoImpl
import javax.inject.Inject


class GetOpenDotaUserUseCase @Inject constructor(
    var openDotaRepoImpl: AppUserRepoImpl,
) {

    suspend fun getOpenDotaResponseOnId(id: String): OpenDotaResponse {
        return openDotaRepoImpl. getResponseFromOpenDota(id)
    }

}

