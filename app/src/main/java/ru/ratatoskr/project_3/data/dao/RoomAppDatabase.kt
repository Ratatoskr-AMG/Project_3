package ru.ratatoskr.project_3.data.dao

import android.content.Context
import androidx.room.*
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.data.converters.HeroesConverterImpl
import ru.ratatoskr.project_3.domain.model.Favorites
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.model.steam.SteamPlayer

@Database(entities = [Hero::class,Favorites::class, SteamPlayer::class], version = 6)
@TypeConverters(HeroesConverterImpl::class)
abstract class RoomAppDatabase : RoomDatabase() {

    abstract fun heroesDao(): HeroesDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun SteamUsersDao(): SteamUsersDao

    companion object {

        fun buildDataSource(context: Context): RoomAppDatabase = Room.databaseBuilder(
            context, RoomAppDatabase::class.java, HeroesContract.databaseApp
        )
            .fallbackToDestructiveMigration()
            .build()
    }


}