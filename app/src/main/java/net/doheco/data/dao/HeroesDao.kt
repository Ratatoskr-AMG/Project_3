package net.doheco.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.doheco.domain.model.Hero

@Dao
interface HeroesDao {
    @get:Query(net.doheco.data.contracts.HeroesContract.fetchHeroes)
    val all: List<Hero>

    @Insert(entity = Hero::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(hero: Hero)

    @Query(net.doheco.data.contracts.HeroesContract.fetchHero)
    suspend fun fetchHero(heroId: Int): Hero

    @Query("SELECT * FROM ${net.doheco.data.contracts.HeroesContract.HEROES_TABLE_NAME} WHERE localizedName LIKE :str")
    suspend fun fetchHeroesByStr(str: String): List<Hero>


}