package ru.ratatoskr.doheco.presentation.screens.recommendations.models

sealed class RecommendationsState {
    class LoadedRecommendationsState<T>(
        val heroes: List<T>,
        val player_tier: String
    ) : RecommendationsState(){

    }
    class LoadingRecommendationsState() : RecommendationsState()
    class NoHeroesRecommendationsState(val msg:String) : RecommendationsState()
}