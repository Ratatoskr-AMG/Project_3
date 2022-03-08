/*
Многопоточность
Параллельность
Асинхронность
3 основных ошибки конкурентности
16.6 мс
В скоуп передается диспетчер, это SuperVisorJob
*/

package ru.ratatoskr.project_3.domain.repository

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import java.lang.Exception
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject

class HeroesRepoImpl @Inject constructor(
    private val roomAppDatabase: RoomAppDatabase,
    private val client: HttpClient
) {

    suspend fun getAllHeroesListFromAPI(): List<Hero> {

        val URL = "https://api.opendota.com/api/heroStats/";
        val strHealhMultiplier = 20;
        val strHealhRegenMultiplier = 0.1;
        val intManaMultiplier = 12;
        val intManaRegenMultiplier = 0.05;
        val agiArmorMultiplier = 0.167;
        val mathContext = MathContext(200, RoundingMode.HALF_DOWN)

        return try {
            val heroes = client.get<List<Hero>>(URL)
            heroes.map {
                it.img = "https://cdn.dota2.com${it.img}"
                it.icon = "https://cdn.dota2.com${it.icon}"
                it.baseHealth = it.baseHealth + it.baseStr * strHealhMultiplier
                it.baseMana = it.baseMana + it.baseInt * intManaMultiplier

                it.baseHealthRegen = (BigDecimal(
                    ((it.baseStr) * strHealhRegenMultiplier) + it.baseHealthRegen,
                    mathContext
                )).setScale(1, BigDecimal.ROUND_HALF_DOWN).toDouble()

                it.baseManaRegen = (BigDecimal(
                    ((it.baseInt) * intManaRegenMultiplier) + it.baseManaRegen,
                    mathContext
                )).setScale(1, BigDecimal.ROUND_HALF_DOWN).toDouble()

                it.baseArmor = BigDecimal(
                    ((it.baseAgi) * agiArmorMultiplier) + it.baseArmor,
                    mathContext
                ).setScale(1, BigDecimal.ROUND_HALF_DOWN).toDouble()

                //roomAppDatabase.heroesDao().insertHero(it)
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
        Heroes.map { Hero ->
            try {
                roomAppDatabase.heroesDao().insertHero(Hero)
            } catch (e: Exception) {
                Log.e("TOHA", e.message.toString())
            }
        }
    }

    suspend fun getAllHeroesByAttr(attr: String): List<Hero> {
        Log.e("TOHA", "attr:" + attr)
        return roomAppDatabase.heroesDao().fetchHeroesByAttr3(attr)
    }
}

