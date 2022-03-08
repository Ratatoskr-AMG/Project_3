package ru.ratatoskr.project_3.data.storage

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.domain.model.Hero

@Dao
interface FavoritesDao {
    @get:Query(HeroesContract.fetchHeroes)
    val all: List<Hero>
/*
    @Query(HeroesContract.DROP_COMMAND)
    fun drop()

    @Query(HeroesContract.CREATE_COMMAND)
    fun create()
 */


    @Insert(entity = Hero::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(hero: Hero)

    @Query(HeroesContract.fetchHero)
    suspend fun fetchHero(heroId: Int): Hero

    @Query("SELECT * FROM " + HeroesContract.HEROES_TABLE_NAME + " ORDER BY " + HeroesContract.COLUMN_LEGS + " DESC")
    suspend fun fetchHeroesByAttr(): List<Hero>

    @Query(HeroesContract.fetchHeroesByAttr)
    suspend fun fetchHeroesByAttr2(attr: String): List<Hero>

    @Query("SELECT * FROM " + HeroesContract.HEROES_TABLE_NAME + " ORDER BY :attr DESC")
    suspend fun fetchHeroesByAttr3(attr: String): List<Hero>
}