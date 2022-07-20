package ru.ratatoskr.project_3.presentation.screens.profile.models

sealed class ProfileState {
    data class UndefinedState(
        var player_tier: String,
        var heroes_list_last_modified: String
    ) : ProfileState()
    data class SteamNameIsDefinedState(
        var player_tier: String,
        var player_steam_name: String,
        var heroes_list_last_modified: String
    ) : ProfileState()
    object ErrorProfileState : ProfileState()
}
