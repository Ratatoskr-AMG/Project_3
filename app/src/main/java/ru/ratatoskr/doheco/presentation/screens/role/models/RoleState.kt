package ru.ratatoskr.doheco.presentation.screens.role.models

import ru.ratatoskr.doheco.domain.model.Hero

sealed class RoleState {
    class LoadingHeroesListState : RoleState()
    class LoadedHeroesListState<T>(
        val heroes: List<T>,
        val player_tier: String,
        val favoriteHeroes: List<Hero>
    ) : RoleState()
    class NoHeroesListState(val message: String) : RoleState()
    class ErrorHeroesListState(val message: String) : RoleState()
}