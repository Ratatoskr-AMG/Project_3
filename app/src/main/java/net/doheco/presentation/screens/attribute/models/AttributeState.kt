package net.doheco.presentation.screens.attribute.models

sealed class AttributeState {
    class LoadingHeroesState : AttributeState()
    class LoadedHeroesState<T>(
        val heroes: List<T>,
        val searchStr: String,
        var isSortAsc: Boolean,
    ) : AttributeState(){

    }
    class NoHeroesState(val message: String) : AttributeState()
    class ErrorHeroesState(val message: String) : AttributeState()
}