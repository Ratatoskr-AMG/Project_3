package ru.ratatoskr.project_3.data.impl

import android.content.Context
import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ratatoskr.project_3.data.remote.implementations.HeroesProvider
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import java.lang.Exception
import javax.inject.Inject

class HeroesRepoImpl @Inject constructor(
    private val roomAppDatabase: RoomAppDatabase,
    private val client: HttpClient
) {


    suspend fun getAllHeroesListFromAPI(): List<Hero> {

        val URL = "https://api.opendota.com/api/heroStats/";


        return try {
            val heroes = client.get<List<Hero>>(URL)
            heroes.map {
                it.img = "https://cdn.dota2.com${it.img}"
                it.icon = "https://cdn.dota2.com${it.icon}"
                //withContext(Dispatchers.IO) {
                    roomAppDatabase.heroesDao().insertHero(it)
               // }
                it
            }
        } catch (e: Exception) {
            error(e)
        }

    }

    fun getAllHeroesListFromDB(): List<Hero> {

        return roomAppDatabase.heroesDao().all
    }

    suspend fun getHeroById(heroId: String): Hero {
        return roomAppDatabase.heroesDao().fetchHero(heroId.toInt())
    }

    suspend fun updateAllHeroesTable(
        Heroes: List<Hero>
    ) {
        //roomAppDatabase.heroesDao().insertHeroes(Heroes)
        Heroes.map { Hero ->
            Log.e("TOHA", Hero.toString())
            try {
                roomAppDatabase.heroesDao().insertHero(Hero)
            } catch (e: Exception) {
                Log.e("TOHA", e.message.toString())
            }
        }

        //for (Hero in Heroes) {
        //    Log.e("TOHA", Hero.toString());
        //roomAppDatabase.heroesDao().insertHero(Hero)
        // }


    }
}

