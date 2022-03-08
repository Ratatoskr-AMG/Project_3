package ru.ratatoskr.project_3.domain.repository.heroes

import android.util.Log
import io.ktor.client.*
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.Hero
import java.lang.Exception
import javax.inject.Inject

/* Отдел по работе с резидентами */

class HeroesSqliteRepoImpl @Inject constructor(
    private val roomAppDatabase: RoomAppDatabase,
    private val client: HttpClient
) {

    fun getAllHeroesListFromDB(): List<Hero> {


        return roomAppDatabase.heroesDao().all


    }

    suspend fun getHeroById(heroId: String): Hero {
        return roomAppDatabase.heroesDao().fetchHero(heroId.toInt())
    }

    suspend fun updateSqliteTable(
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
}

