package ru.ratatoskr.doheco.domain.utils

import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.presentation.screens.hero.models.HeroState

class AttributeMaximum(
    val name: String,
    val value: Number
){
    fun getvalue():Number{
        return value
    }
}