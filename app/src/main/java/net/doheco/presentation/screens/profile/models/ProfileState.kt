package net.doheco.presentation.screens.profile.models

import net.doheco.domain.model.DotaMatch
import net.doheco.domain.model.opendota.OpenDotaMatch

sealed class ProfileState {
    data class UndefinedState(
        var player_tier: String,
        var heroes_list_last_modified: String,
        var btnText: String
    ) : ProfileState()
    data class SteamNameIsDefinedState(
        var player_tier: String,
        var player_steam_name: String,
        var heroes_list_last_modified: String,
        var btnText: String,
        var matchesList: List<DotaMatch>? = emptyList()
    ) : ProfileState()
    object ErrorProfileState : ProfileState()
    object UpdateHeroesAndMatchesError : ProfileState()
    data class Init(
        var player_tier: String = "0",
    ) : ProfileState()
}
