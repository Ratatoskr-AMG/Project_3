package net.doheco.domain.useCases.user

import net.doheco.domain.model.opendota.OpenDotaResponse
import net.doheco.domain.repository.AppUserRepoImpl
import javax.inject.Inject

class SendFeedbackUseCase @Inject constructor(
    var appUserRepoImpl: AppUserRepoImpl,
) {

    suspend fun sendFeedbackUseCase(name: String,text: String): String {
        return appUserRepoImpl.sendFeedback(name,text)
    }

}