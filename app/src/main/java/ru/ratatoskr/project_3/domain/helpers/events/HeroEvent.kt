package ru.ratatoskr.project_3.domain.helpers.events

sealed class HeroEvent {
    data class OnFavoriteCLick(val heroId: Int, val newValue: Boolean) : HeroEvent()
}
