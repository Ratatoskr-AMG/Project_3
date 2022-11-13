package net.doheco.domain.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.doheco.domain.utils.GetResource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvideResources {

    @Singleton
    @Provides
    fun provideGetResource(@ApplicationContext context: Context): GetResource {
        return GetResource(context)
    }
}


