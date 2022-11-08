package net.doheco.presentation.screens.comparing.models

import net.doheco.domain.model.Hero

sealed class ComparingState {
    data class HeroesState(
        val left: Hero,
        val right: Hero,
        val heroes: List<Hero>,
        val favoriteHeroes: List<Hero>,
        val currentInfoBlock: String
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