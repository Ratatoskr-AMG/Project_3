package ru.ratatoskr.project_3.presentation.screens.account.profile.models

sealed class ProfileEvent {
    object OnSteamExit : ProfileEvent()
}
