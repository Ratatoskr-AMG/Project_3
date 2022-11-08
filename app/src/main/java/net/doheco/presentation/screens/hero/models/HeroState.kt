package net.doheco.presentation.screens.hero.models
import net.doheco.domain.model.Hero
import net.doheco.domain.utils.AttributeMaximum

sealed class HeroState {
    class NoHeroState : HeroState()
    class LoadingHeroState : HeroState()
    class ErrorHeroState : HeroState()
    data class HeroLoadedState(
        val hero: Hero,
        val isFavorite: Boolean,
        val currentInfoBlock: String,
        val currentAttrsMax: List<AttributeMaximum>,
    ) : HeroState()
}

