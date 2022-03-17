package ru.ratatoskr.project_3.domain.di

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import javax.inject.Singleton


//Cпец. класс в котором объявлены зависимости
//scope http bd converters -> app.scope = @Singleton
//Модуль привязан к lifecycle = @InstallIn
//Внутри обычных классов @Provides (@Bind - для абстрактных)
//Не можем повлиять на класс (библиотека), а только создать = @Provides
//Все view : Context , @ApplicationContext
//Когда подтягиваем ресурсы (строки, картинки): если передан Апп контекст то нет проблем, контекст вьюхи не подойдет


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): RoomAppDatabase = RoomAppDatabase.buildDataSource(context = context)
}

