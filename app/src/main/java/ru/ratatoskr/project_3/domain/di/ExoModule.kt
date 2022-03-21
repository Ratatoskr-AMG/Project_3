package ru.ratatoskr.project_3.domain.di

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExoModule {

    @Provides
    @Singleton
    fun provideExoPlayer(
        @ApplicationContext context: Context
    ): SimpleExoPlayer = SimpleExoPlayer.Builder(context).build()
}

