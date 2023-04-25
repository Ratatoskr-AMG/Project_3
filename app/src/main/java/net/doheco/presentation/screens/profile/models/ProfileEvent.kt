package net.doheco.presentation.screens.profile.models

sealed class ProfileEvent {
    object OnSteamExit : ProfileEvent()
    data class OnUpdate(val friendCode: String = ""): ProfileEvent()
    object OnAPICallResultScreenBoxClose : ProfileEvent()
    data class OnSendFeedback(val name: String,val text: String): ProfileEvent()
}
