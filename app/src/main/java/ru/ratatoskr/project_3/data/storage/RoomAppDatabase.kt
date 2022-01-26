package ru.ratatoskr.project_3.data.storage

import android.content.Context
<<<<<<< HEAD
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
=======
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
>>>>>>> origin/master
import ru.ratatoskr.project_3.data.storage.contracts.RoomContract
import ru.ratatoskr.project_3.domain.model.Hero

@Database(entities = [Hero::class], version = 1)
abstract class RoomAppDatabase: RoomDatabase() {

    abstract fun heroesDao(): HeroesDao

    companion object {

        fun buildDataSource(context: Context): RoomAppDatabase = Room.databaseBuilder(
            context, RoomAppDatabase::class.java, RoomContract.databaseApp)
            .build()
    }


}