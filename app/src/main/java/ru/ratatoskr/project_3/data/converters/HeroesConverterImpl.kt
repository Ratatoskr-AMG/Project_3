package ru.ratatoskr.project_3.data.converters

import ru.ratatoskr.project_3.domain.model.Hero

class HeroesConverterImpl: HeroesConverter {
    override fun convertFromApiToDB(hero: Hero): Hero {

        return hero
    }

}