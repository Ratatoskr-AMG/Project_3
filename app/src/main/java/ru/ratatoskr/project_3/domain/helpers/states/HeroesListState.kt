package ru.ratatoskr.project_3.domain.helpers.states

sealed class HeroesListState {
    class LoadingHeroesListState : HeroesListState()
    class LoadedHeroesListState<T>(
        val heroes: List<T>,
        val searchStr: String,
        var isSortAsc: Boolean,
    ) : HeroesListState(){

    }
    class NoHeroesListState(val message: String) : HeroesListState()
    class ErrorHeroesListState(val message: String) : HeroesListState()
}