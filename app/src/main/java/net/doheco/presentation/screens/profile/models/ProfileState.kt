package net.doheco.presentation.screens.profile.models

import net.doheco.domain.model.DotaMatch
import net.doheco.domain.model.opendota.OpenDotaMatch

sealed class ProfileState {

    data class EmptyState(
        var playerTier: String = "0",
        var msg: String = "",
    ) : ProfileState()

    data class UndefinedState(
        var playerTier: String = "0",
        var msg: String = "",
    ) : ProfileState()

    data class SteamDefinedState(
        var playerTier: String,
        var playerSteamName: String,
        var playerMatchesList: List<DotaMatch>? = emptyList(),
        var msg: String = ""
    ) : ProfileState()

    data class  ErrorProfileState(var msg: String = "") : ProfileState()

    data class  APICallResultProfileState(
        var playerTier: String,
        var playerSteamName: String,
        var msg: String = ""

    ) : ProfileState()
}
