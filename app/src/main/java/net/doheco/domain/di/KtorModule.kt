package net.doheco.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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