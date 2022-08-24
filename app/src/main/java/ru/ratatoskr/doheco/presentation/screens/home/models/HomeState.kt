package ru.ratatoskr.doheco.presentation.screens.home.models

sealed class HomeState {
    class LoadingHomeState : HomeState()
    class LoadedHomeState<T>(
        val heroes: List<T>,
        val searchStr: String,
        var isSortAsc: Boolean,
    ) : HomeState(){

    }
    class NoHomeState(val message: String) : HomeState()
    class ErrorHomeState(val message: String) : HomeState()
}