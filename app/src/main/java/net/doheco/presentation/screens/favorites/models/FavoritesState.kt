package net.doheco.presentation.screens.favorites.models

import net.doheco.domain.model.Hero

sealed class FavoritesState {
    class LoadingHeroesState : FavoritesState()
    class LoadedHeroesState<T>(
        val heroes: List<Hero>
    ) : FavoritesState(){

    }
    class NoHeroesState(val message: String) : FavoritesState()
    class ErrorHeroesState(val message: String) : FavoritesState()
}