package ru.ratatoskr.project_3.presentation.screens.favorites.models

import ru.ratatoskr.project_3.domain.model.Hero

sealed class FavoritesState {
    class LoadingHeroesState : FavoritesState()
    class LoadedHeroesState<T>(
        val heroes: List<Hero>
    ) : FavoritesState(){

    }
    class NoHeroesState(val message: String) : FavoritesState()
    class ErrorHeroesState(val message: String) : FavoritesState()
}