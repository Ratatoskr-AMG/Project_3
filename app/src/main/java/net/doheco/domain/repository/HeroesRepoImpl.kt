package net.doheco.domain.repository

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import net.doheco.data.dao.HeroesDao
import net.doheco.domain.model.Hero
import net.doheco.domain.model.opendota.OpenDotaMatch
import javax.inject.Inject
import kotlin.Exception

class HeroesRepoImpl @Inject constructor(
    private val dao: HeroesDao,
    private val client: HttpClient
) {
    fun getAllHeroesList(): List<Hero> {
        return dao.all
    }

    suspend fun getAllHeroesListByStr(str:String): List<Hero> {
        if(str!="") {
            return dao.fetchHeroesByStr("$str%")
        }else{
            return dao.all
        }
    }

    suspend fun getHeroById(heroId: String): Hero {
        return dao.fetchHero(heroId.toInt())
    }

    suspend fun updateHeroesList(
        Heroes: List<Hero>
    ) {
        Heroes.map { Hero ->
            try {
                dao.insertHero(Hero)
            } catch (e: Exception) {
                Log.e("TOHA", "updateSqliteTable e: " + e.message.toString())
            }finally{
                Log.e("TOHA", "updateSqliteTable")
            }
        }
    }

    suspend fun getAllHeroesListFromAPI(steamId:String): List<Hero> {
        //val URL = "https://api.opendota.com/api/heroStats/";
        //val URL = "https://api.opendota.com/api/players/{account_id}/recentMatches";
        val URL = "https://doheco.net/api/heroStats/?id="+steamId+"&r="+(1000000..9999999999).random();

        try {
            val result : List<Hero> = client.get(URL)
            Log.e("TOHA", "client.get(URL)"+client.get(URL))
            Log.e("TOHA", "client.get(URL)result$result")
            return result
        } catch (e: Exception) {
            error(e)
            return emptyList()
        }
    }

    suspend fun getMatchesFormApi(steamId: String): List<OpenDotaMatch> {
        val url = "https://api.opendota.com/api/players/$steamId/matches"
        try {
            return client.get(url)
        } catch (e: Exception) {
            error(e)
        }
    }

}


