package ru.ratatoskr.project_3.data.impl

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.data.HeroesRepo
import ru.ratatoskr.project_3.domain.model.Hero

object HeroesRepoImpl : HeroesRepo{

    override suspend fun getAllHeroesList(): List<Hero> {

        val client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }

        val URL ="https://api.opendota.com/api/heroStats/";

        return client.get(URL)

    }

}

