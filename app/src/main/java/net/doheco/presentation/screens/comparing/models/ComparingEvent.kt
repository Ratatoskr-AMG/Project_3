package net.doheco.presentation.screens.comparing.models

sealed class ComparingEvent {
    data class OnFavoriteCLick(val heroId: Int, val newValue: Boolean) : ComparingEvent()
    data class OnInfoBlockSelect(val infoBlockName: String) : ComparingEvent()
}
