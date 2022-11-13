package net.doheco.domain.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import net.doheco.domain.utils.GetResource
import javax.inject.Singleton

@Module
@InstallIn(SinceKotlin::class)
class ProvideResources {

    @Singleton
    @Provides
    fun provideGetResource(@ApplicationContext context: Context): GetResource {
        return GetResource(context)
    }
}


