package net.doheco.presentation.screens.recommendations.models

import net.doheco.domain.model.Hero
import net.doheco.presentation.screens.profile.models.ProfileState

sealed class RecommendationsState {
    class LoadedRecommendationsState<T>(
        val heroes: List<T>,
        val player_tier: String,
        val favoriteHeroes: List<Hero>

    ) : RecommendationsState(){

    }
    object LoadingRecommendationsState : RecommendationsState()
    object RefreshingRecommendationsState : RecommendationsState()
    class NoHeroesRecommendationsState(val msg:String) : RecommendationsState()
    data class  ErrorRecommendationsState(var msg: String = "") : RecommendationsState()
}