package ru.ratatoskr.doheco.domain.repository

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import ru.ratatoskr.doheco.data.dao.RoomAppDatabase
import ru.ratatoskr.doheco.domain.model.Hero
import java.lang.Exception
import javax.inject.Inject
class HeroesRepoImpl @Inject constructor(
    private val roomAppDatabase: RoomAppDatabase,
    private val client: HttpClient
) {

    fun getAllHeroesList(): List<Hero> {
        return roomAppDatabase.heroesDao().all
    }

    suspend fun getAllHeroesListByStr(str:String): List<Hero> {
        if(str!="") {
            return roomAppDatabase.heroesDao().fetchHeroesByStr(str + "%")
        }else{
            return roomAppDatabase.heroesDao().all
        }
    }

    suspend fun getHeroById(heroId: String): Hero {
        return roomAppDatabase.heroesDao().fetchHero(heroId.toInt())
    }

    suspend fun updateHeroesList(
        Heroes: List<Hero>
    ) {
        Heroes.map { Hero ->
            try {
                roomAppDatabase.heroesDao().insertHero(Hero)
            } catch (e: Exception) {
                Log.e("TOHA", "updateSqliteTable e: " + e.message.toString())
            }finally{
                Log.e("TOHA", "updateSqliteTable")
            }
        }


    }

    suspend fun getAllHeroesListFromAPI(): List<Hero> {


        val URL = "https://api.opendota.com/api/heroStats/";

        return try {

            client.get(URL)

        } catch (e: Exception) {
            error(e)
        }

    }

}


