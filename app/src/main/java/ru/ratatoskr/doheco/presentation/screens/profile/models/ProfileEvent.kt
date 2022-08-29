package ru.ratatoskr.doheco.presentation.screens.profile.models

sealed class ProfileEvent {
    object OnSteamExit : ProfileEvent()
    object OnUpdate : ProfileEvent()
}
