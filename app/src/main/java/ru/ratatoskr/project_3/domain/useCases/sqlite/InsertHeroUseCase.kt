package ru.ratatoskr.project_3.domain.useCases.sqlite

import android.util.Log
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesSqliteRepoImpl
import java.lang.Exception
import javax.inject.Inject

class InsertHeroesUseCase @Inject constructor(private val localRepoImpl: HeroesSqliteRepoImpl) {

    suspend fun InsertHeroesToDB(heroes: List<Hero>) {
        return localRepoImpl.updateSqliteTable(heroes)
    }

}