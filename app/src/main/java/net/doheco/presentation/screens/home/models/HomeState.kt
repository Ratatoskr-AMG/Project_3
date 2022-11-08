package net.doheco.presentation.screens.home.models

import net.doheco.domain.model.Hero

sealed class HomeState {
    class LoadingHomeState : HomeState()
    class LoadedHomeState<T>(
        val heroes: List<T>,
        val searchStr: String,
        var isSortAsc: Boolean,
        val favoriteHeroes: List<Hero>
    ) : HomeState(){

    }
    class NoHomeState(val message: String) : HomeState()
    class ErrorHomeState(val message: String) : HomeState()
}