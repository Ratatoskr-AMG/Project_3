package ru.ratatoskr.project_3.presentation.screens.account.profile.models

sealed class ProfileEvent {
    //data class OnSteamLogin(val steam_user_id: String) : ProfileEvent()
    //data class OnTierChange(val selected_tier: String) : ProfileEvent()
    object OnSteamExit : ProfileEvent()
}
