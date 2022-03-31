package ru.ratatoskr.project_3.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.model.SteamPlayer

@Dao
interface HeroesDao {
    @get:Query(HeroesContract.fetchHeroes)
    val all: List<Hero>

    @Insert(entity = Hero::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(hero: Hero)

    @Query(HeroesContract.fetchHero)
    suspend fun fetchHero(heroId: Int): Hero

    @Query("SELECT * FROM ${HeroesContract.HEROES_TABLE_NAME} WHERE localizedName LIKE :str")
    suspend fun fetchHeroesByStr(str: String): List<Hero>


}