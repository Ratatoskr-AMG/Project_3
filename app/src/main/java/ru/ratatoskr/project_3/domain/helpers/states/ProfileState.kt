package ru.ratatoskr.project_3.domain.helpers.states

sealed class ProfileState {
    data class IndefinedState(
        var playerTier: String,
        var heroes_list_last_modified: String
    ) : ProfileState()
    object LoadingState : ProfileState()
    object ErrorProfileState : ProfileState()
    data class LoggedIntoSteam(
        val steam_user_id: String,
        val steam_user_avatar: String,
        val steam_user_name: String,
        var player_tier: String,
        var heroes_list_last_modified: String,
    ) : ProfileState()
}
