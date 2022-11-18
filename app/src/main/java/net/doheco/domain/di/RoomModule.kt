package net.doheco.domain.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.doheco.data.contracts.HeroesContract
import net.doheco.data.dao.FavoritesDao
import net.doheco.data.dao.HeroesDao
import net.doheco.data.dao.RoomAppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoomAppDatabase {
        return Room.databaseBuilder(
            context,
            RoomAppDatabase::class.java,
            HeroesContract.databaseApp
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideHeroesDao(db: RoomAppDatabase): HeroesDao {
        return db.heroesDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(db: RoomAppDatabase): FavoritesDao {
        return db.favoritesDao()
    }

}

