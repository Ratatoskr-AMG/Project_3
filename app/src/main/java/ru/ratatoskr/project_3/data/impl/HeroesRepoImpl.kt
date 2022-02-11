package ru.ratatoskr.project_3.data.impl

import android.content.Context
import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.data.remote.implementations.HeroesProvider
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import java.lang.Exception
import javax.inject.Inject

class HeroesRepoImpl @Inject constructor(private val roomAppDatabase: RoomAppDatabase) {

    suspend fun getAllHeroesListFromAPI(): List<Hero> {

        val URL = "https://api.opendota.com/api/heroStats/";

        val client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }

        var result = client.get<List<Hero>>(URL);

        return result

    }

    fun getAllHeroesListFromDB(): List<Hero> {
        return roomAppDatabase.heroesDao().all
    }

    fun updateAllHeroesTable(
        Heroes: List<Hero>
    ) {
        for (Hero in Heroes) {
            Log.d("TOHA", Hero.name);
            roomAppDatabase.heroesDao().insertHero(Hero)
        }
    }
}

