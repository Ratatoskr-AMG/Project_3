package ru.ratatoskr.doheco.domain.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ratatoskr.doheco.data.dao.RoomAppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SPModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences(
        "app_preferences", Context.MODE_PRIVATE
    )
}