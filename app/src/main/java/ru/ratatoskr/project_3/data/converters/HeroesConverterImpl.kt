package ru.ratatoskr.project_3.data.converters

import android.util.Log
import androidx.room.TypeConverter
import ru.ratatoskr.project_3.domain.model.Hero
import java.util.*
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