package ru.ratatoskr.project_3.domain.helpers.states

import ru.ratatoskr.project_3.domain.extensions.set

sealed class HeroListState {
    class LoadingHeroListState : HeroListState()
    class LoadedHeroListState<T>(
        val heroes: List<T>,
        val searchStr: String,
        var isSortAsc: Boolean,
    ) : HeroListState(){

    }
    class NoHeroListState(val message: String) : HeroListState()
    class ErrorHeroListState(val message: String) : HeroListState()
}