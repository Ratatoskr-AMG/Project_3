package net.doheco.presentation.screens.hero.models

import androidx.compose.foundation.lazy.LazyListState

sealed class HeroEvent {
    data class OnFavoriteCLick(val heroId: Int, val newValue: Boolean) : HeroEvent()
    data class OnInfoBlockSelect(val infoBlockName: String,val scrollState: LazyListState) : HeroEvent()
}
