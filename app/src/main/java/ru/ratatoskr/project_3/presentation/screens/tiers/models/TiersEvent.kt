package ru.ratatoskr.project_3.presentation.screens.tiers.models

sealed class TiersEvent {
    data class OnTierChange(val selected_tier: String) : TiersEvent()
}
