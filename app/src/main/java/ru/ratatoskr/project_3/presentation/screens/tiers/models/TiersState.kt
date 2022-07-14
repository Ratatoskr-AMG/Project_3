package ru.ratatoskr.project_3.presentation.screens.tiers.models

sealed class TiersState {
    data class IndefinedState(
        var player_tier: String,
    ) : TiersState()
    data class DefinedState(
        var player_tier: String,
    ) : TiersState()
    object ErrorTiersState: TiersState()

}
