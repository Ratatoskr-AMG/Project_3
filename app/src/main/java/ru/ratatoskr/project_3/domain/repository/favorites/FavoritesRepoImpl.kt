package ru.ratatoskr.project_3.domain.repository.favorites

import io.ktor.client.request.*
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.useCases.calculations.BasicIndicatorsUseCase
import java.lang.Exception
import javax.inject.Inject

class FavoritesSqliteRepoImpl  @Inject constructor(
    private val roomAppDatabase: RoomAppDatabase) {

    suspend fun getFavorites(): List<Hero> {
        return roomAppDatabase.heroesDao().all
    }
}