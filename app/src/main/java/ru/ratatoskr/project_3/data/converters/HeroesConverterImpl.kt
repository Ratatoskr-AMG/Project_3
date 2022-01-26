package ru.ratatoskr.project_3.data.converters

import androidx.room.TypeConverter
import java.util.stream.Collectors

class HeroesConverterImpl: HeroesConverter {

    @TypeConverter
    override fun fromRoles(roles: List<String>): String {
        return roles.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    override fun toRoles(roles: String): List<String> {
        return listOf(roles.split(",").toString())
    }

}