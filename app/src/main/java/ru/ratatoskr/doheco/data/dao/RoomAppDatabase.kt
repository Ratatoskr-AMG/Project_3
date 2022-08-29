package ru.ratatoskr.doheco.data.dao

import android.content.Context
import androidx.room.*
import ru.ratatoskr.doheco.data.contracts.HeroesContract
import ru.ratatoskr.doheco.data.converters.HeroesConverterImpl
import ru.ratatoskr.doheco.domain.model.Favorites
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.model.steam.SteamPlayer

@Database(entities = [Hero::class,Favorites::class, SteamPlayer::class], version = 6)
@TypeConverters(HeroesConverterImpl::class)
abstract class RoomAppDatabase : RoomDatabase() {

    abstract fun heroesDao(): HeroesDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun SteamUsersDao(): SteamUsersDao

    companion object {

        fun buildDataSource(context: Context): RoomAppDatabase = Room.databaseBuilder(
            context, RoomAppDatabase::class.java, ru.ratatoskr.doheco.data.contracts.HeroesContract.databaseApp
        )
            .fallbackToDestructiveMigration()
            .build()
    }


}