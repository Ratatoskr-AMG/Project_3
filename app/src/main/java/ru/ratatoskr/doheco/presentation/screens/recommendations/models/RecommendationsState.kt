package ru.ratatoskr.doheco.presentation.screens.recommendations.models

import ru.ratatoskr.doheco.domain.model.Hero

sealed class RecommendationsState {
    class LoadedRecommendationsState<T>(
        val heroes: List<T>,
        val player_tier: String,
        val favoriteHeroes: List<Hero>

    ) : RecommendationsState(){

    }
    class LoadingRecommendationsState() : RecommendationsState()
    class NoHeroesRecommendationsState(val msg:String) : RecommendationsState()
}