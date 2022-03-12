package ru.ratatoskr.project_3.domain.helpers

sealed class HeroesListState {
    class LoadingHeroesListState: HeroesListState()
    class LoadedHeroesListState<T>(val data: List<T>): HeroesListState()
    class HeroLoadedHeroesListState<Hero>(val data: Hero): HeroesListState()
    class NoHeroesListState: HeroesListState()
    class ErrorHeroesListState(val message: String): HeroesListState()
}