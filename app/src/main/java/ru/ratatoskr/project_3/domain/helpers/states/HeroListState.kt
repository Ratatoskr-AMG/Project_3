package ru.ratatoskr.project_3.domain.helpers.states

sealed class HeroListState {
    class LoadingHeroListState : HeroListState()
    class LoadedHeroListState<T>(val heroes: List<T>) : HeroListState()
    class NoHeroListState(val message: String) : HeroListState()
    class ErrorHeroListState(val message: String) : HeroListState()
}