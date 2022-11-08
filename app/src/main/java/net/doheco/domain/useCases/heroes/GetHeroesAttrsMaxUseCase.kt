package net.doheco.domain.useCases.heroes

import android.util.Log
import net.doheco.domain.extensions.toArrayList
import net.doheco.domain.repository.HeroesRepoImpl
import net.doheco.domain.utils.AttributeMaximum
import net.doheco.domain.utils.appUtilsArrays
import javax.inject.Inject


class GetHeroesAttrsMaxUseCase @Inject constructor(val localRepoImpl: HeroesRepoImpl) {

    suspend fun getHeroesAttrsMax(): List<AttributeMaximum> {
        var attributesMaximums: MutableList<AttributeMaximum> = arrayListOf()
        var heroes = localRepoImpl.getAllHeroesList().toArrayList()

        for (attr in appUtilsArrays.heroesAttrs()) {
            when (attr) {
                "legs" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.legs }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "legs", descendingHeroes[0].legs,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "baseHealth" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseHealth }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseHealth",
                            descendingHeroes[0].baseHealth,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "baseHealthRegen" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseHealthRegen }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseHealthRegen",
                            descendingHeroes[0].baseHealthRegen,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "baseMana" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseMana }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseMana",
                            descendingHeroes[0].baseMana,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "baseManaRegen" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseManaRegen }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseManaRegen",
                            descendingHeroes[0].baseManaRegen,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "baseArmor" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseArmor }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseArmor",
                            descendingHeroes[0].baseArmor,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "baseMr" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseMr }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseMr", descendingHeroes[0].baseMr,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "baseStr" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseStr }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseStr", descendingHeroes[0].baseStr,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "baseAgi" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseAgi }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseAgi", descendingHeroes[0].baseAgi,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "baseInt" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.baseInt }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "baseInt", descendingHeroes[0].baseInt,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "strGain" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.strGain }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "strGain", descendingHeroes[0].strGain,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "agiGain" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.agiGain }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "agiGain", descendingHeroes[0].agiGain,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "intGain" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.intGain }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "intGain", descendingHeroes[0].intGain,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "attackRange" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.attackRange }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "attackRange",
                            descendingHeroes[0].attackRange,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "projectileSpeed" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.projectileSpeed }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "projectileSpeed",
                            descendingHeroes[0].projectileSpeed,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "attackRate" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.attackRate }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "attackRate",
                            descendingHeroes[0].attackRate,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "moveSpeed" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.moveSpeed }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "moveSpeed",
                            descendingHeroes[0].moveSpeed,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "turboPicks" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.turboPicks }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "turboPicks",
                            descendingHeroes[0].turboPicks,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "turboWins" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.turboWins }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "turboWins",
                            descendingHeroes[0].turboWins,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "proBan" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.proBan }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "proBan", descendingHeroes[0].proBan,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "proWin" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.proWin }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "proWin", descendingHeroes[0].proWin,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "proPick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it.proPick }
                    Log.e("TOHA", "descendingHeroes:" + descendingHeroes.toString())
                    attributesMaximums.add(
                        AttributeMaximum(
                            "proPick", descendingHeroes[0].proPick,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_1Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._1Pick }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_1Pick", descendingHeroes[0]._1Pick,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_1Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._1Win }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_1Win", descendingHeroes[0]._1Win,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_2Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._2Pick }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_2Pick", descendingHeroes[0]._2Pick,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_2Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._2Win }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_2Win", descendingHeroes[0]._2Win,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_3Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._3Pick }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_3Pick", descendingHeroes[0]._3Pick,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_3Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._3Win }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_3Win", descendingHeroes[0]._3Win,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_4Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._4Pick }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_4Pick", descendingHeroes[0]._4Pick,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_4Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._4Win }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_4Win", descendingHeroes[0]._4Win,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_5Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._5Pick }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_5Pick", descendingHeroes[0]._5Pick,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_5Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._5Win }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_5Win", descendingHeroes[0]._5Win,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_6Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._6Pick }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_6Pick", descendingHeroes[0]._6Pick,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_6Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._6Win }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_6Win", descendingHeroes[0]._6Win,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_7Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._7Pick }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_7Pick", descendingHeroes[0]._7Pick,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_7Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._7Win }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_7Win", descendingHeroes[0]._7Win,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_8Pick" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._8Pick }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_8Pick", descendingHeroes[0]._8Pick,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_8Win" -> {
                    var descendingHeroes = heroes.sortedByDescending { it._8Win }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_8Win", descendingHeroes[0]._8Win,
                            descendingHeroes[0].icon
                        )
                    )
                }
                "turboWinrate" -> {
                    var descendingHeroes = heroes.sortedByDescending {
                        String.format(
                            "%.4f",
                            it.turboWins.toFloat() / it.turboPicks.toFloat()
                        )
                    }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "turboWinrate",
                            String.format(
                                "%.4f",
                                descendingHeroes[0].turboWins.toFloat() / descendingHeroes[0].turboPicks.toFloat()
                            ).toFloat(),
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_1Winrate" -> {
                    var descendingHeroes = heroes.sortedByDescending {
                        String.format(
                            "%.4f",
                            it._1Win.toFloat() / it._1Pick.toFloat()
                        )
                    }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_1Winrate",
                            String.format(
                                "%.4f",
                                descendingHeroes[0]._1Win.toFloat() / descendingHeroes[0]._1Pick.toFloat()
                            ).toFloat(),
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_2Winrate" -> {
                    var descendingHeroes = heroes.sortedByDescending {
                        String.format(
                            "%.4f",
                            it._2Win.toFloat() / it._2Pick.toFloat()
                        )
                    }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_2Winrate",
                            String.format(
                                "%.4f",
                                descendingHeroes[0]._2Win.toFloat() / descendingHeroes[0]._2Pick.toFloat()
                            ).toFloat(),
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_3Winrate" -> {
                    var descendingHeroes = heroes.sortedByDescending {
                        String.format(
                            "%.4f",
                            it._3Win.toFloat() / it._3Pick.toFloat()
                        )
                    }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_3Winrate",
                            String.format(
                                "%.4f",
                                descendingHeroes[0]._3Win.toFloat() / descendingHeroes[0]._3Pick.toFloat()
                            ).toFloat(),
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_4Winrate" -> {
                    var descendingHeroes = heroes.sortedByDescending {
                        String.format(
                            "%.4f",
                            it._4Win.toFloat() / it._4Pick.toFloat()
                        )
                    }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_4Winrate",
                            String.format(
                                "%.4f",
                                descendingHeroes[0]._4Win.toFloat() / descendingHeroes[0]._4Pick.toFloat()
                            ).toFloat(),
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_5Winrate" -> {
                    var descendingHeroes = heroes.sortedByDescending {
                        String.format(
                            "%.4f",
                            it._5Win.toFloat() / it._5Pick.toFloat()
                        )
                    }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_5Winrate",
                            String.format(
                                "%.4f",
                                descendingHeroes[0]._5Win.toFloat() / descendingHeroes[0]._5Pick.toFloat()
                            ).toFloat(),
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_6Winrate" -> {
                    var descendingHeroes = heroes.sortedByDescending {
                        String.format(
                            "%.4f",
                            it._6Win.toFloat() / it._6Pick.toFloat()
                        )
                    }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_6Winrate",
                            String.format(
                                "%.4f",
                                descendingHeroes[0]._6Win.toFloat() / descendingHeroes[0]._6Pick.toFloat()
                            ).toFloat(),
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_7Winrate" -> {
                    var descendingHeroes = heroes.sortedByDescending {
                        String.format(
                            "%.4f",
                            it._7Win.toFloat() / it._7Pick.toFloat()
                        )
                    }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_7Winrate",
                            String.format(
                                "%.4f",
                                descendingHeroes[0]._7Win.toFloat() / descendingHeroes[0]._7Pick.toFloat()
                            ).toFloat(),
                            descendingHeroes[0].icon
                        )
                    )
                }
                "_8Winrate" -> {
                    var descendingHeroes = heroes.sortedByDescending {
                        String.format(
                            "%.4f",
                            it._8Win.toFloat() / it._8Pick.toFloat()
                        )
                    }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "_8Winrate",
                            String.format(
                                "%.4f",
                                descendingHeroes[0]._8Win.toFloat() / descendingHeroes[0]._8Pick.toFloat()
                            ).toFloat(),
                            descendingHeroes[0].icon
                        )
                    )
                }
                "proWinrate" -> {
                    var descendingHeroes = heroes.sortedByDescending {
                        String.format(
                            "%.4f",
                            it.proWin.toFloat() / it.proPick.toFloat()
                        )
                    }
                    attributesMaximums.add(
                        AttributeMaximum(
                            "proWinrate",
                            String.format(
                                "%.4f",
                                descendingHeroes[0].proWin.toFloat() / descendingHeroes[0].proPick.toFloat()
                            ).toFloat(),
                            descendingHeroes[0].icon
                        )
                    )
                }
                else -> heroes.sortedByDescending { it.localizedName }
            }
        }

        return attributesMaximums
    }


}
