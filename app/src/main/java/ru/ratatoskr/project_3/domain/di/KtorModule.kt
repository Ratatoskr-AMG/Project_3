package ru.ratatoskr.project_3.domain.di

import android.content.Context
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class KtorModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient = HttpClient(Android) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }
}