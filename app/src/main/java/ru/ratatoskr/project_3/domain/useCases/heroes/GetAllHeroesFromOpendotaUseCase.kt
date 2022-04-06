package ru.ratatoskr.project_3.domain.useCases.heroes

import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.HeroesRepoImpl
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject


class GetAllHeroesFromOpendotaUseCase @Inject constructor(
    var opendotaRepoImpl: HeroesRepoImpl,
) {
    suspend fun getAllHeroesFromApi(): List<Hero> {
        return calculate(opendotaRepoImpl.getAllHeroesListFromAPI())
    }

    fun calculate(heroes: List<Hero>): List<Hero> {
        val strHealhMultiplier = 20;
        val strHealhRegenMultiplier = 0.1;
        val intManaMultiplier = 12;
        val intManaRegenMultiplier = 0.05;
        val agiArmorMultiplier = 0.167;
        val mathContext = MathContext(200, RoundingMode.HALF_DOWN)

        heroes.map {

            it.img = "https://cdn.dota2.com${it.img}"
            it.icon = "https://cdn.dota2.com${it.icon}"
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