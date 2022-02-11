package ru.ratatoskr.project_3.domain.helpers

//(c)пиздил
sealed class State {
    //Работа совершается
    class LoadingState: State()
    //Работа совершена (результат - список объектов <любого типа>)
    class LoadedState<T>(val data: List<T>): State()
    //Работа совершена (без результатов)
    class NoItemsState: State()
    //Ошибка при совершении работы (результат - сообщение<строка>)
    class ErrorState(val message: String): State()
}