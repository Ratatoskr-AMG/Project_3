package ru.ratatoskr.doheco.domain.utils

import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero

object appUtilsArrays {


    fun startAlignHeroesArray(): List<String> {
        return listOf(
            "Alchemist",
            "Bristleback",
            "Drow Ranger",
            "Huskar",
            "Keeper of the Light",
            "Lina",
            "Marci",
            "Pudge",
            "Shadow Shaman",
            "Tidehunter",
            "Troll Warlord",
            "Ursa",
        )
    }

    fun endAlignHeroesArray(): List<String> {
        return listOf(
            "Death Prophet",
            "Anti-Mage",
            "Axe",
            "Brewmaster",
            "Clockwerk",
            "Crystal Maiden",
            "Dragon Knight",
            "Grimstroke",
            "Leshrac",
            "Lifestealer",
            "Lone Druid",
            "Lycan",
            "Meepo",
            "Necrophos",
            "Night Stalker",
            "Pangolier",
            "Puck",
            "Razor",
            "Riki",
            "Timbersaw",
            "Venomancer",
            "Weaver",
            "Zeus",
        )
    }

    fun rolesLanguageMap(): Map<String, Int> {
        return mapOf(
            "Carry" to R.string.carry_role,
            "Escape" to R.string.escape_role,
            "Nuker" to R.string.nuker_role,
            "Support" to R.string.support_role,
            "Disabler" to R.string.disabler_role,
            "Jungler" to R.string.jungler_role,
            "Initiator" to R.string.initiator_role,
            "Durable" to R.string.durable_role,
            "Pusher" to R.string.pusher_role,
        )
    }

    fun rolesMultipleLanguageMap(): Map<String, Int> {
        return mapOf(
            "Carry" to R.string.carry_role_multiple,
            "Escape" to R.string.escape_role_multiple,
            "Nuker" to R.string.nuker_role_multiple,
            "Support" to R.string.support_role_multiple,
            "Disabler" to R.string.disabler_role_multiple,
            "Jungler" to R.string.jungler_role_multiple,
            "Initiator" to R.string.initiator_role_multiple,
            "Durable" to R.string.durable_role_multiple,
            "Pusher" to R.string.pusher_role_multiple,
        )
    }

    fun heroesAttrs(): List<String> {
        return listOf(
            "baseHealth",
            "baseMana",
            "baseHealthRegen",
            "baseManaRegen",
            "baseArmor",
            "baseStr",
            "baseAgi",
            "baseInt",
            "strGain",
            "agiGain",
            "intGain",
            "attackRange",
            "projectileSpeed",
            "attackRate",
            "moveSpeed",
            "turboPicks",
            "turboWins",
            "proBan",
            "proWin",
            "proPick",
            "_1Pick",
            "_1Win",
            "_2Pick",
            "_2Win",
            "_3Pick",
            "_3Win",
            "_4Pick",
            "_4Win",
            "_5Pick",
            "_5Win",
            "_6Pick",
            "_6Win",
            "_7Pick",
            "_7Win",
            "_8Pick",
            "_8Win",

        )

    }

    fun attrsLanguageMap(): Map<String, Int> {
        return mapOf(
            "baseHealth" to R.string.base_health_attr,
            "baseMana" to R.string.base_mana_attr,
            "baseHealthRegen" to R.string.base_health_regen_attr,
            "baseManaRegen" to R.string.base_mana_regen_attr,
            "baseArmor" to R.string.base_armor_attr,
            "baseStr" to R.string.base_str_attr,
            "baseAgi" to R.string.base_agi_attr,
            "baseInt" to R.string.base_int_attr,
            "strGain" to R.string.str_gain_attr,
            "agiGain" to R.string.agi_gain_attr,
            "intGain" to R.string.int_gain_attr,
            "attackRange" to R.string.attack_range_attr,
            "projectileSpeed" to R.string.projectile_speed_attr,
            "attackRate" to R.string.attack_rate_attr,
            "moveSpeed" to R.string.move_speed_attr,
            "turboPicks" to R.string.turbo_picks_attr,
            "turboWins" to R.string.turbo_wins_attr,
            "proBan" to R.string.pro_ban_attr,
            "proWin" to R.string.pro_win_attr,
            "proPick" to R.string.pro_pick_attr,
            "_1Pick" to R.string._1_pick_attr,
            "_1Win" to R.string._1_win_attr,
            "_2Pick" to R.string._2_pick_attr,
            "_2Win" to R.string._2_win_attr,
            "_3Pick" to R.string._3_pick_attr,
            "_3Win" to R.string._3_win_attr,
            "_4Pick" to R.string._4_pick_attr,
            "_4Win" to R.string._4_win_attr,
            "_5Pick" to R.string._5_pick_attr,
            "_5Win" to R.string._5_win_attr,
            "_6Pick" to R.string._6_pick_attr,
            "_6Win" to R.string._6_win_attr,
            "_7Pick" to R.string._7_pick_attr,
            "_7Win" to R.string._7_win_attr,
            "_8Pick" to R.string._8_pick_attr,
            "_8Win" to R.string._8_win_attr

        )
    }

    fun heroImgContentAlign(hero: Hero): Alignment {
        var cAlign: Alignment = Alignment.TopCenter

        if (hero.localizedName in endAlignHeroesArray()) {
            cAlign = Alignment.TopEnd
        }

        return cAlign
    }


}
