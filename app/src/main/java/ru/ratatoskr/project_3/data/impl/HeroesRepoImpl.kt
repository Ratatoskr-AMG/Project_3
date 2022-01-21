package ru.ratatoskr.project_3.data.impl

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.data.HeroesDBHelper
import ru.ratatoskr.project_3.data.HeroesRepo
import ru.ratatoskr.project_3.domain.model.Hero

object HeroesRepoImpl : HeroesRepo{

    override suspend fun getAllHeroesList(context: Context): List<Hero> {

        val URL ="https://api.opendota.com/api/heroStats/";

        val client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }

        var result = client.get<List<Hero>>(URL);

        return result

    }

}

