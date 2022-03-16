package ru.ratatoskr.project_3.domain.helpers.states

sealed class ProfileState {
    object IndefinedState : ProfileState()
    object LoadingState : ProfileState()
    object ErrorProfileState : ProfileState()
    data class LoggedIntoSteam(
        val steam_user_id: String,
        val steam_user_avatar: String,
        val steam_user_name: String,
    ) : ProfileState()
}
