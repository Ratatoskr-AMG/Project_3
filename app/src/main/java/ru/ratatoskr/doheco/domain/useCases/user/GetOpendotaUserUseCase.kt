package ru.ratatoskr.doheco.domain.useCases.user


import ru.ratatoskr.doheco.domain.model.opendota.OpenDotaResponse
import ru.ratatoskr.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject


class GetOpenDotaUserUseCase @Inject constructor(
    var openDotaRepoImpl: AppUserRepoImpl,
) {

    suspend fun getOpenDotaResponseOnId(id: String): OpenDotaResponse {
        return openDotaRepoImpl. getResponseFromOpenDota(id)
    }

}

