package ru.ratatoskr.project_3.data.storage

import androidx.room.*
import ru.ratatoskr.project_3.data.HeroesContract
import ru.ratatoskr.project_3.data.storage.contracts.HeroesSqlContract
import ru.ratatoskr.project_3.domain.model.Hero


@Dao
interface HeroesDao {
    @get:Query(HeroesSqlContract.fetch)
    val all: List<Hero>

    @Insert(entity = Hero::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertHero(hero: Hero)

}