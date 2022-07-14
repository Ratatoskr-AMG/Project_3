package ru.ratatoskr.project_3.presentation.screens.favorites.models

sealed class FavoritesState {
    class LoadingHeroesState : FavoritesState()
    class LoadedHeroesState<T>(
        val heroes: List<T>,
        val searchStr: String,
        var isSortAsc: Boolean,
    ) : FavoritesState(){

    }
    class NoHeroesState(val message: String) : FavoritesState()
    class ErrorHeroesState(val message: String) : FavoritesState()
}