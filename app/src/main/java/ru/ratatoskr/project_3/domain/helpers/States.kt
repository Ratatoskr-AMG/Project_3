package ru.ratatoskr.project_3.domain.helpers

sealed class State {
    class LoadingState: State()
    class LoadedState<T>(val data: List<T>): State()
    class HeroLoadedState<Hero>(val data: Hero, var isFavorite: Boolean): State()
    class NoItemsState: State()
    class ErrorState(val message: String): State()
}

sealed class FavoriteState {
    class Yes: FavoriteState()
    class No: FavoriteState()
    class Error: FavoriteState()

}