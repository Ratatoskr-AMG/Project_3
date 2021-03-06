package ru.ratatoskr.project_3.presentation.screens.hero.models
import ru.ratatoskr.project_3.domain.model.Hero

sealed class HeroState {
    class NoHeroState : HeroState()
    class LoadingHeroState : HeroState()
    class ErrorHeroState : HeroState()
    data class HeroLoadedState(
        val hero: Hero,
        val isFavorite: Boolean,
    ) : HeroState()
}
