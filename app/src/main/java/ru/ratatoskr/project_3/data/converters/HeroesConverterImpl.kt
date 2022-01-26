package ru.ratatoskr.project_3.data.converters

import ru.ratatoskr.project_3.domain.model.Hero

class HeroesConverterImpl: HeroesConverter {
    override fun convertFromApiToDB(hero: Hero): Hero {
        var rolesString = ""
        for(role in hero.roles){
            if(rolesString!="")rolesString+=","
            rolesString+=role
        }
        hero.roles=rolesString
        return hero
    }

}