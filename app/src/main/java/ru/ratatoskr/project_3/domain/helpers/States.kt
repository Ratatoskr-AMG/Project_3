package ru.ratatoskr.project_3.domain.helpers

sealed class HeroesListState {
    class LoadingHeroesListState : HeroesListState()
    class LoadedHeroesListState<T>(val data: List<T>) : HeroesListState()
    class HeroLoadedHeroesListState<Hero>(val data: Hero) : HeroesListState()
    class NoHeroesListState : HeroesListState()
    class ErrorHeroesListState(val message: String) : HeroesListState()
}

sealed class HeroState {
    class HeroLoadedState<Hero>(val hero: Hero, var isFavorite: Boolean) : HeroState()

    class NoHeroState : HeroState()
    class LoadingHeroState : HeroState()
    class ErrorHeroState : HeroState()
    data class Display(
        val id: Int,
        val isFavorite: Boolean,
    ) : HeroState()
}