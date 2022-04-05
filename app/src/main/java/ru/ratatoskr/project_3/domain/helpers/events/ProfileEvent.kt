package ru.ratatoskr.project_3.domain.helpers.events

sealed class ProfileEvent {
    data class OnSteamLogin(val steam_user_id: String) : ProfileEvent()
    object OnSteamExit : ProfileEvent()
}
