package net.doheco.data.dao

import androidx.room.*
import net.doheco.data.converters.HeroesConverterImpl
import net.doheco.domain.model.Favorites
import net.doheco.domain.model.Hero
import net.doheco.domain.model.steam.SteamPlayer

@Database(entities = [Hero::class,Favorites::class, SteamPlayer::class], version = 6)
@TypeConverters(HeroesConverterImpl::class)
abstract class RoomAppDatabase : RoomDatabase() {
    abstract fun heroesDao(): HeroesDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun SteamUsersDao(): SteamUsersDao
}