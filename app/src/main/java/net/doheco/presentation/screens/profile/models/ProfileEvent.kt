package net.doheco.presentation.screens.profile.models

sealed class ProfileEvent {
    object OnSteamExit : ProfileEvent()
    object OnUpdate : ProfileEvent()
    object OnUndefinedProfileUpdate : ProfileEvent()
    object OnDefinedProfileUpdate : ProfileEvent()
    data class OnSendFeedback(val name: String,val text: String): ProfileEvent()
}
