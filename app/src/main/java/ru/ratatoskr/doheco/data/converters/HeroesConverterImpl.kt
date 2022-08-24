package ru.ratatoskr.doheco.data.converters
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.util.stream.Collectors

class HeroesConverterImpl: ru.ratatoskr.doheco.data.converters.HeroesConverter {

    @RequiresApi(Build.VERSION_CODES.N)
    @TypeConverter
    override fun fromRoles(roles: List<String>): String {
        return roles.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    override fun toRoles(roles: String): List<String> {
        return listOf(roles.split(",").toString())
    }

}