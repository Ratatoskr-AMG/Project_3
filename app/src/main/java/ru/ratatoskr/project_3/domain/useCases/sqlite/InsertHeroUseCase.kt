package ru.ratatoskr.project_3.domain.useCases.sqlite

import android.util.Log
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.Hero
import java.lang.Exception
import javax.inject.Inject

class InsertHeroUseCase @Inject constructor(private val roomAppDatabase: RoomAppDatabase) {

    suspend fun insert(heroes:List<Hero>) {
        heroes.map()
        {
            Hero ->
            try {
                roomAppDatabase.heroesDao().insertHero(Hero)
            } catch (e: Exception) {
                Log.e("TOHA", e.message.toString())
            }
        }
    }

}