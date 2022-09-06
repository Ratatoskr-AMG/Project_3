package ru.ratatoskr.doheco.presentation.screens.comparing.models

import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.presentation.screens.hero.models.HeroState

sealed class ComparingState {
    data class HeroesState(
        val left: Hero,
        val right: Hero,
        val heroes: List<Hero>,
    ) : ComparingState(

    )
    class LoadingState : ComparingState()
    data class SelectHeroesState(
        val left: Hero,
        val right: Hero,
        val isLeft: Boolean,
        val heroes: List<Hero>,
    ) : ComparingState()
}