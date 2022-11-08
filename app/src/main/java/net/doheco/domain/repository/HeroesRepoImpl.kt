package net.doheco.domain.repository

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import net.doheco.data.dao.RoomAppDatabase
import net.doheco.domain.model.Hero
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

    suspend fun getAllHeroesListFromAPI(steamId:String): List<Hero> {


        //val URL = "https://api.opendota.com/api/heroStats/";
        val URL = "https://doheco.net/api/heroStats/?id="+steamId+"&r="+(1000000..9999999999).random();

        try {
            var result : List<Hero> = client.get(URL)
            Log.e("TOHA", "client.get(URL)"+client.get(URL))
            Log.e("TOHA", "client.get(URL)result"+result.toString())
            return result
        } catch (e: Exception) {
            error(e)
            return emptyList()
        }


    }

}


