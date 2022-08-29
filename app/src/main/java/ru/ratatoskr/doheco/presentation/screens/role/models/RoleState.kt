package ru.ratatoskr.doheco.presentation.screens.role.models

sealed class RoleState {
    class LoadingHeroesListState : RoleState()
    class LoadedHeroesListState<T>(
        val heroes: List<T>
    ) : RoleState()
    class NoHeroesListState(val message: String) : RoleState()
    class ErrorHeroesListState(val message: String) : RoleState()
}