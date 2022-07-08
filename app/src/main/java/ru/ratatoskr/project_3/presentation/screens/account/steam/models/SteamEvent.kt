package ru.ratatoskr.project_3.presentation.screens.account.steam.models

sealed class SteamEvent {
    data class OnSteamLogin(val steam_user_id: String) : SteamEvent()
}
