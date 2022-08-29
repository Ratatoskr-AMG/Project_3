package ru.ratatoskr.doheco.presentation.screens.hero.models
import ru.ratatoskr.doheco.domain.model.Hero

sealed class HeroState {
    class NoHeroState : HeroState()
    class LoadingHeroState : HeroState()
    class ErrorHeroState : HeroState()
    data class HeroLoadedState(
        val hero: Hero,
        val isFavorite: Boolean,
    ) : HeroState()
}
