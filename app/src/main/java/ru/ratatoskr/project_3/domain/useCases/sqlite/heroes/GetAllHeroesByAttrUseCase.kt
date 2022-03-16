package ru.ratatoskr.project_3.domain.useCases.sqlite.heroes

import ru.ratatoskr.project_3.domain.extensions.toArrayList
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesSqliteRepoImpl
import javax.inject.Inject
import kotlin.reflect.full.memberProperties


class GetAllHeroesByAttrUseCase @Inject constructor(
    val localRepoImpl: HeroesSqliteRepoImpl
) {
    fun getAllHeroesByAttr(attr: String): List<Hero> {
        var heroes = localRepoImpl.getAllHeroesList().toArrayList()

        return when (attr) {
            "legs" -> heroes.sortedByDescending { it.legs }
            "baseHealth" -> heroes.sortedByDescending { it.baseHealth }
            "baseHealthRegen" -> heroes.sortedByDescending { it.baseHealthRegen }
            "baseMana" -> heroes.sortedByDescending { it.baseMana }
            "baseManaRegen" -> heroes.sortedByDescending { it.baseManaRegen }
            "baseArmor" -> heroes.sortedByDescending { it.baseArmor }
            "baseMr" -> heroes.sortedByDescending { it.baseMr }
            "baseStr" -> heroes.sortedByDescending { it.baseStr }
            "baseAgi" -> heroes.sortedByDescending { it.baseAgi }
            "baseInt" -> heroes.sortedByDescending { it.baseInt }
            "strGain" -> heroes.sortedByDescending { it.strGain }
            "agiGain" -> heroes.sortedByDescending { it.agiGain }
            "intGain" -> heroes.sortedByDescending { it.intGain }
            "attackRange" -> heroes.sortedByDescending { it.attackRange }
            "projectileSpeed" -> heroes.sortedByDescending { it.projectileSpeed }
            "attackRate" -> heroes.sortedByDescending { it.attackRate }
            "moveSpeed" -> heroes.sortedByDescending { it.moveSpeed }
            "cmEnabled" -> heroes.sortedByDescending { it.cmEnabled }
            "turboPicks" -> heroes.sortedByDescending { it.turboPicks }
            "turboWins" -> heroes.sortedByDescending { it.turboWins }
            "proBan" -> heroes.sortedByDescending { it.proBan }
            "proWin" -> heroes.sortedByDescending { it.proWin }
            "proPick" -> heroes.sortedByDescending { it.proPick }
            "_1Pick" -> heroes.sortedByDescending { it._1Pick }
            "_1Win" -> heroes.sortedByDescending { it._1Win }
            "_2Pick" -> heroes.sortedByDescending { it._2Pick }
            "_2Win" -> heroes.sortedByDescending { it._2Win }
            "_3Pick" -> heroes.sortedByDescending { it._3Pick }
            "_3Win" -> heroes.sortedByDescending { it._3Win }
            "_4Pick" -> heroes.sortedByDescending { it._4Pick }
            "_4Win" -> heroes.sortedByDescending { it._4Win }
            "_5Pick" -> heroes.sortedByDescending { it._5Pick }
            "_5Win" -> heroes.sortedByDescending { it._5Win }
            "_6Pick" -> heroes.sortedByDescending { it._6Pick }
            "_6Win" -> heroes.sortedByDescending { it._6Win }
            "_7Pick" -> heroes.sortedByDescending { it._7Pick }
            "_7Win" -> heroes.sortedByDescending { it._7Win }
            "_8Pick" -> heroes.sortedByDescending { it._8Pick }
            "_8Win" -> heroes.sortedByDescending { it._8Win }
            else -> heroes.sortedBy { it.localizedName }
        }
    }

    @Throws(IllegalAccessException::class, ClassCastException::class)
    inline fun <reified T> Any.getField(fieldName: String): T? {
        this::class.memberProperties.forEach { kCallable ->
            if (fieldName == kCallable.name) {
                return kCallable.getter.call(this) as T?
            }
        }
        return null
    }

}

