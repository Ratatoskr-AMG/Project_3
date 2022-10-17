package ru.ratatoskr.doheco.domain.useCases.heroes

import android.util.Log
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.repository.HeroesRepoImpl
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject


class GetAllHeroesFromOpendotaUseCase @Inject constructor(
    var opendotaRepoImpl: HeroesRepoImpl,
) {
    suspend fun getAllHeroesFromApi(steamId:String): List<Hero> {
        return calculate(opendotaRepoImpl.getAllHeroesListFromAPI(steamId))
    }

    fun calculate(heroes: List<Hero>): List<Hero> {
        Log.e("TOHA","heroessize:"+heroes.size)
        val strHealhMultiplier = 20;
        val strHealhRegenMultiplier = 0.1;
        val intManaMultiplier = 12;
        val intManaRegenMultiplier = 0.05;
        val agiArmorMultiplier = 0.167;
        val mathContext = MathContext(200, RoundingMode.HALF_DOWN)
        val imagesbaseUrl = "https://cdn.dota2.com"
        val imagesbaseUrl2 = "http://ratatoskr.ru"

        heroes.map {

            it.img = imagesbaseUrl+it.img
            it.icon = imagesbaseUrl+it.icon
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

        return heroes
    }
}