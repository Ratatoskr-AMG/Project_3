package ru.ratatoskr.project_3.data.converters

import ru.ratatoskr.project_3.domain.model.Hero

interface HeroesConverter {
    fun convertFromApiToDB(hero: Hero): Hero
}