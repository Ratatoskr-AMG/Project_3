package ru.ratatoskr.project_3.data.storage

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.data.converters.HeroesConverterImpl
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.activity.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Database(entities = [Hero::class], version = 2)
@TypeConverters(HeroesConverterImpl::class)
abstract class RoomAppDatabase: RoomDatabase() {

    abstract fun heroesDao(): HeroesDao

    companion object {

        fun buildDataSource(context: Context): RoomAppDatabase = Room.databaseBuilder(
            context, RoomAppDatabase::class.java, HeroesContract.databaseApp
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
