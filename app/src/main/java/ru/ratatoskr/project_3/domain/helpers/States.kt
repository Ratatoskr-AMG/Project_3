package ru.ratatoskr.project_3.domain.helpers

sealed class State {
    class LoadingState: State()
    class LoadedState<T>(val data: List<T>): State()
    class NoItemsState: State()
    class ErrorState(val message: String): State()
}