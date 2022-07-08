package ru.ratatoskr.project_3.presentation.screens.account.tiers.models

sealed class TiersState {
    data class IndefinedState(
        var player_tier: String,
    ) : TiersState()

}
