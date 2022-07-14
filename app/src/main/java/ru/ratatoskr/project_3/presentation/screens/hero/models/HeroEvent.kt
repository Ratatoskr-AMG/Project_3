package ru.ratatoskr.project_3.presentation.screens.hero.models

sealed class HeroEvent {
    data class OnFavoriteCLick(val heroId: Int, val newValue: Boolean) : HeroEvent()
}
