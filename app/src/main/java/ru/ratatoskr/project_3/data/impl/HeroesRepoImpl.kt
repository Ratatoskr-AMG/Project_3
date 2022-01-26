package ru.ratatoskr.project_3.data.impl

import android.content.Context
import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.data.HeroesRepo
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase

class HeroesRepoImpl : HeroesRepo {

    override suspend fun getAllHeroesListFromAPI(): List<Hero> {

        val URL = "https://api.opendota.com/api/heroStats/";

        val client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }

        var result = client.get<List<Hero>>(URL);

        return result

    }
    override fun getAllHeroesListFromDB(roomAppDatabase: RoomAppDatabase): List<Hero>{
        return roomAppDatabase.heroesDao().all
    }

    override fun updateAllHeroesTable(roomAppDatabase: RoomAppDatabase, context: Context, Heroes: List<Hero>) {
        for (Hero in Heroes) {
            Log.d("TOHA",Hero.name);
            roomAppDatabase.heroesDao().insertHero(Hero)
        }
    }
}

