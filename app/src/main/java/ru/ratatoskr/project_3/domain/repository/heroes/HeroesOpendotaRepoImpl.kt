/*
Многопоточность
Параллельность
Асинхронность
3 основных ошибки конкурентности
16.6 мс
В скоуп передается диспетчер, это SuperVisorJob
*/

package ru.ratatoskr.project_3.domain.repository.heroes

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.useCases.calculations.BasicIndicatorsUseCase
import java.lang.Exception
import javax.inject.Inject

/* Отдел по работе с нашими западными партнёрами (НЗП) */

class HeroesOpendotaRepoImpl @Inject constructor(
    private val roomAppDatabase: RoomAppDatabase,
    private val client: HttpClient
) {

    /* Колцентр (HttpClient) */

    suspend fun getAllHeroesListFromAPI(): List<Hero> {

        val URL = "https://api.opendota.com/api/heroStats/";

        return try {

            BasicIndicatorsUseCase().calculate(client.get(URL))

        } catch (e: Exception) {
            error(e)
        }

    }


    suspend fun getAllHeroesByAttr(attr: String): List<Hero> {
        Log.e("TOHA", "attr:" + attr)
        return roomAppDatabase.heroesDao().fetchHeroesByAttr3(attr)
    }
}

