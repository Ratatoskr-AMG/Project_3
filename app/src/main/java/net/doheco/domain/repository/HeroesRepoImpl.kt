package net.doheco.domain.repository

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import net.doheco.data.dao.HeroesDao
import net.doheco.data.dao.MatchDao
import net.doheco.domain.model.DotaMatch
import net.doheco.domain.model.Hero
import net.doheco.domain.model.HeroesAndMatchesApiCallResult
import net.doheco.domain.model.opendota.OpenDotaMatch
import javax.inject.Inject
import kotlin.Exception

class HeroesRepoImpl @Inject constructor(
    private val dao: HeroesDao,
    private val client: HttpClient,
    private val matchDao: MatchDao
) {
    fun getAllHeroesList(): List<Hero> {
        return dao.all
    }

    suspend fun getAllHeroesListByStr(str: String): List<Hero> {
        return if (str != "") {
            dao.fetchHeroesByStr("$str%")
        } else {
            dao.all
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
            } finally {
                Log.e("TOHA", "updateSqliteTable")
            }
        }
    }

    suspend fun getAllHeroesListFromAPI(steamId: String): List<Hero> {
        //val URL = "https://api.opendota.com/api/heroStats/";
        //val URL = "https://api.opendota.com/api/players/{account_id}/recentMatches";
        val URL =
            "https://doheco.net/api/heroStats/?id=" + steamId + "&r=" + (1000000..9999999999).random();

        Log.e("TOHA","URL:"+URL)

        try {
            val result: List<Hero> = client.get(URL)
            Log.e("TOHA", "client.get(URL)" + client.get(URL))
            Log.e("TOHA", "client.get(URL)result$result")
            Log.e("TOHA","URL:norm")
            return result
        } catch (e: Exception) {
            Log.e("TOHA","URL:error")
            error(e)
            return emptyList()
        }
    }

    suspend fun getMatchesFormApi(steamId: String): List<OpenDotaMatch> {
        val url = "https://api.opendota.com/api/players/$steamId/matches?limit=50"
        try {
            return client.get(url)
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun addToDb(list: List<DotaMatch>) {
        Log.e("APICALL","list:"+list.toString())
        matchDao.insertAll(list)
    }

    suspend fun getFromDb(): List<DotaMatch> {
        return matchDao.getAll()
    }

    suspend fun getFromDbInId(id: Long): DotaMatch {
        return matchDao.getFromId(id)
    }

    suspend fun deleteAllMatchesFromDb() {
        matchDao.deleteAll()
    }
}


