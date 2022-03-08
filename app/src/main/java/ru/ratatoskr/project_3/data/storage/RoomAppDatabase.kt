package ru.ratatoskr.project_3.data.storage

import android.content.Context
import androidx.room.*
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.data.converters.HeroesConverterImpl
import ru.ratatoskr.project_3.domain.model.Hero

@Database(entities = [Hero::class], version = 2)
@TypeConverters(HeroesConverterImpl::class)
abstract class RoomAppDatabase : RoomDatabase() {

    abstract fun heroesDao(): HeroesDao
    abstract fun favoritesDao(): FavoritesDao

    companion object {

        fun buildDataSource(context: Context): RoomAppDatabase = Room.databaseBuilder(
            context, RoomAppDatabase::class.java, HeroesContract.databaseApp
        )
            .fallbackToDestructiveMigration()
            .build()
    }


}