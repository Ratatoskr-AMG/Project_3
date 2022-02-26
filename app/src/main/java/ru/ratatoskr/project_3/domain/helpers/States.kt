package ru.ratatoskr.project_3.domain.helpers

import ru.ratatoskr.project_3.domain.model.Hero

sealed class State {
    class LoadingState: State()
    class LoadedState<T>(val data: List<T>): State()
    class HeroLoadedState<Hero>(val data: Hero): State()
    class NoItemsState: State()
    class ErrorState(val message: String): State()
}