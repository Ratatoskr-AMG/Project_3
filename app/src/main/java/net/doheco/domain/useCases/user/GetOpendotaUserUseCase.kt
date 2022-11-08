package net.doheco.domain.useCases.user


import net.doheco.domain.model.opendota.OpenDotaResponse
import net.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject


class GetOpenDotaUserUseCase @Inject constructor(
    var openDotaRepoImpl: AppUserRepoImpl,
) {

    suspend fun getOpenDotaResponseOnId(id: String): OpenDotaResponse {
        return openDotaRepoImpl. getResponseFromOpenDota(id)
    }

}

