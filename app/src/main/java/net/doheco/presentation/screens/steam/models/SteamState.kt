package net.doheco.presentation.screens.steam.models

sealed class SteamState {

    data class ErrorSteamState(
        val error: String
    ): SteamState()

    data class IndefinedState(
        val playerTier: String
    ): SteamState()

    data class LoggedIntoSteam(
        val steamUserId: String,
        val steamUserAvatar: String,
        val steamUserName: String,
        var playerTier: String,
    ) : SteamState()
}
