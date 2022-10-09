package ru.ratatoskr.doheco.domain.useCases.heroes

import android.util.Log
import ru.ratatoskr.doheco.domain.extensions.toArrayList
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.repository.HeroesRepoImpl
import ru.ratatoskr.doheco.domain.utils.AttributeMaximum
import ru.ratatoskr.doheco.domain.utils.appUtilsArrays
import javax.inject.Inject


class GetHeroesAttrsMaxUseCase @Inject constructor(val localRepoImpl: HeroesRepoImpl) {
    suspend fun getHeroesAttrsMax(): List<AttributeMaximum> {
        var attributesMaximums: MutableList<AttributeMaximum> = arrayListOf()
        var heroes = localRepoImpl.getAllHeroesList().toArrayList()

        for (attr in appUtilsArrays.heroesAttrs()) {
            when (attr) {
                "legs" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.legs }
                    attributesMaximums.add(AttributeMaximum("legs", descendingHeroes[0].legs))
                }
                "baseHealth" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseHealth }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseHealth",
                            descendingHeroes[0].baseHealth
                        )
                    )
                }
                "baseHealthRegen" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseHealthRegen }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseHealthRegen",
                            descendingHeroes[0].baseHealthRegen
                        )
                    )
                }
                "baseMana" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseMana }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseMana",
                            descendingHeroes[0].baseMana
                        )
                    )
                }
                "baseManaRegen" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseManaRegen }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseManaRegen",
                            descendingHeroes[0].baseManaRegen
                        )
                    )
                }
                "baseArmor" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseArmor }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseArmor",
                            descendingHeroes[0].baseArmor
                        )
                    )
                }
                "baseMr" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseMr }
                    attributesMaximums.add(AttributeMaximum("baseMr", descendingHeroes[0].baseMr))
                }
                "baseStr" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseStr }
                    attributesMaximums.add(AttributeMaximum("baseStr", descendingHeroes[0].baseStr))
                }
                "baseAgi" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseAgi }
                    attributesMaximums.add(AttributeMaximum("baseAgi", descendingHeroes[0].baseAgi))
                }
                "baseInt" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseInt }
                    attributesMaximums.add(AttributeMaximum("baseInt", descendingHeroes[0].baseInt))
                }
                "strGain" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.strGain }
                    attributesMaximums.add(AttributeMaximum("strGain", descendingHeroes[0].strGain))
                }
                "agiGain" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.agiGain }
                    attributesMaximums.add(AttributeMaximum("agiGain", descendingHeroes[0].agiGain))
                }
                "intGain" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.intGain }
                    attributesMaximums.add(AttributeMaximum("intGain", descendingHeroes[0].intGain))
                }
                "attackRange" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.attackRange }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "attackRange",
                            descendingHeroes[0].attackRange
                        )
                    )
                }
                "projectileSpeed" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.projectileSpeed }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "projectileSpeed",
                            descendingHeroes[0].projectileSpeed
                        )
                    )
                }
                "attackRate" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.attackRate }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "attackRate",
                            descendingHeroes[0].attackRate
                        )
                    )
                }
                "moveSpeed" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.moveSpeed }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "moveSpeed",
                            descendingHeroes[0].moveSpeed
                        )
                    )
                }
                "turboPicks" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.turboPicks }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "turboPicks",
                            descendingHeroes[0].turboPicks
                        )
                    )
                }
                "turboWins" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.turboWins }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "turboWins",
                            descendingHeroes[0].turboWins
                        )
                    )
                }
                "proBan" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.proBan }
                    attributesMaximums.add(AttributeMaximum("proBan", descendingHeroes[0].proBan))
                }
                "proWin" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.proWin }
                    attributesMaximums.add(AttributeMaximum("proWin", descendingHeroes[0].proWin))
                }
                "proPick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.proPick }
                    Log.e("TOHA", "descendingHeroes:" + descendingHeroes.toString())
                    attributesMaximums.add(AttributeMaximum("proPick", descendingHeroes[0].proPick))
                }
                "_1Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._1Pick }
                    attributesMaximums.add(AttributeMaximum("_1Pick", descendingHeroes[0]._1Pick))
                }
                "_1Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._1Win }
                    attributesMaximums.add(AttributeMaximum("_1Win", descendingHeroes[0]._1Win))
                }
                "_2Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._2Pick }
                    attributesMaximums.add(AttributeMaximum("_2Pick", descendingHeroes[0]._2Pick))
                }
                "_2Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._2Win }
                    attributesMaximums.add(AttributeMaximum("_2Win", descendingHeroes[0]._2Win))
                }
                "_3Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._3Pick }
                    attributesMaximums.add(AttributeMaximum("_3Pick", descendingHeroes[0]._3Pick))
                }
                "_3Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._3Win }
                    attributesMaximums.add(AttributeMaximum("_3Win", descendingHeroes[0]._3Win))
                }
                "_4Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._4Pick }
                    attributesMaximums.add(AttributeMaximum("_4Pick", descendingHeroes[0]._4Pick))
                }
                "_4Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._4Win }
                    attributesMaximums.add(AttributeMaximum("_4Win", descendingHeroes[0]._4Win))
                }
                "_5Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._5Pick }
                    attributesMaximums.add(AttributeMaximum("_5Pick", descendingHeroes[0]._5Pick))
                }
                "_5Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._5Win }
                    attributesMaximums.add(AttributeMaximum("_5Win", descendingHeroes[0]._5Win))
                }
                "_6Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._6Pick }
                    attributesMaximums.add(AttributeMaximum("_6Pick", descendingHeroes[0]._6Pick))
                }
                "_6Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._6Win }
                    attributesMaximums.add(AttributeMaximum("_6Win", descendingHeroes[0]._6Win))
                }
                "_7Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._7Pick }
                    attributesMaximums.add(AttributeMaximum("_7Pick", descendingHeroes[0]._7Pick))
                }
                "_7Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._7Win }
                    attributesMaximums.add(AttributeMaximum("_7Win", descendingHeroes[0]._7Win))
                }
                "_8Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._8Pick }
                    attributesMaximums.add(AttributeMaximum("_8Pick", descendingHeroes[0]._8Pick))
                }
                "_8Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._8Win }
                    attributesMaximums.add(AttributeMaximum("_8Win", descendingHeroes[0]._8Win))
                }
                else -> heroes.sortedByDescending { it.localizedName }
            }
        }

        return attributesMaximums
    }


}
