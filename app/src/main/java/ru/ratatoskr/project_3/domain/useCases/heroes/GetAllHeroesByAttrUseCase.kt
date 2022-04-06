package ru.ratatoskr.project_3.domain.useCases.heroes

import ru.ratatoskr.project_3.domain.extensions.toArrayList
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.repository.HeroesRepoImpl
import javax.inject.Inject


class GetAllHeroesByAttrUseCase @Inject constructor(
    val localRepoImpl: HeroesRepoImpl
) {


    fun getAllHeroesByAttr(attr: String, sortAsc: Boolean): List<Hero> {
        var heroes = localRepoImpl.getAllHeroesList().toArrayList()
        if (!sortAsc) {
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
        else {
            return when (attr) {
                "legs" -> heroes.sortedBy { it.legs }
                "baseHealth" -> heroes.sortedBy { it.baseHealth }
                "baseHealthRegen" -> heroes.sortedBy { it.baseHealthRegen }
                "baseMana" -> heroes.sortedBy { it.baseMana }
                "baseManaRegen" -> heroes.sortedBy { it.baseManaRegen }
                "baseArmor" -> heroes.sortedBy { it.baseArmor }
                "baseMr" -> heroes.sortedBy { it.baseMr }
                "baseStr" -> heroes.sortedBy { it.baseStr }
                "baseAgi" -> heroes.sortedBy { it.baseAgi }
                "baseInt" -> heroes.sortedBy { it.baseInt }
                "strGain" -> heroes.sortedBy { it.strGain }
                "agiGain" -> heroes.sortedBy { it.agiGain }
                "intGain" -> heroes.sortedBy { it.intGain }
                "attackRange" -> heroes.sortedBy { it.attackRange }
                "projectileSpeed" -> heroes.sortedBy { it.projectileSpeed }
                "attackRate" -> heroes.sortedBy { it.attackRate }
                "moveSpeed" -> heroes.sortedBy { it.moveSpeed }
                "cmEnabled" -> heroes.sortedBy { it.cmEnabled }
                "turboPicks" -> heroes.sortedBy { it.turboPicks }
                "turboWins" -> heroes.sortedBy { it.turboWins }
                "proBan" -> heroes.sortedBy { it.proBan }
                "proWin" -> heroes.sortedBy { it.proWin }
                "proPick" -> heroes.sortedBy { it.proPick }
                "_1Pick" -> heroes.sortedBy { it._1Pick }
                "_1Win" -> heroes.sortedBy { it._1Win }
                "_2Pick" -> heroes.sortedBy { it._2Pick }
                "_2Win" -> heroes.sortedBy { it._2Win }
                "_3Pick" -> heroes.sortedBy { it._3Pick }
                "_3Win" -> heroes.sortedBy { it._3Win }
                "_4Pick" -> heroes.sortedBy { it._4Pick }
                "_4Win" -> heroes.sortedBy { it._4Win }
                "_5Pick" -> heroes.sortedBy { it._5Pick }
                "_5Win" -> heroes.sortedBy { it._5Win }
                "_6Pick" -> heroes.sortedBy { it._6Pick }
                "_6Win" -> heroes.sortedBy { it._6Win }
                "_7Pick" -> heroes.sortedBy { it._7Pick }
                "_7Win" -> heroes.sortedBy { it._7Win }
                "_8Pick" -> heroes.sortedBy { it._8Pick }
                "_8Win" -> heroes.sortedBy { it._8Win }
                else -> heroes.sortedBy { it.localizedName }
            }
        }
    }
}

