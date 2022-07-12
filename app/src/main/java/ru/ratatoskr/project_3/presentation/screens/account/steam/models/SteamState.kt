package ru.ratatoskr.project_3.presentation.screens.account.steam.models

sealed class SteamState {

    object ErrorSteamState: SteamState()
    data class IndefinedState(
        val player_tier: String
    ): SteamState()

    data class LoggedIntoSteam(
        val steam_user_id: String,
        val steam_user_avatar: String,
        val steam_user_name: String,
        var player_tier: String,
        var heroes_list_last_modified: String,
    ) : SteamState()
}
