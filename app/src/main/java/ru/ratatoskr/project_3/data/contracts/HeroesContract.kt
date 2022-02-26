package ru.ratatoskr.project_3.data.contracts

import android.provider.BaseColumns

class HeroesContract {
    companion object HeroesEntries : BaseColumns {
        const val databaseApp = "heroes"
        const val COLUMN_LOCALIZED_NAME = "localizedName"
        const val HEROES_TABLE_NAME = "heroes"
        const val fetchHeroes = "SELECT * FROM " + HEROES_TABLE_NAME + " ORDER BY "+COLUMN_LOCALIZED_NAME+" ASC"
        const val fetchHero =
            "SELECT * FROM " + HEROES_TABLE_NAME + " WHERE id = :heroId"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRIMARY_ATTR = "primaryAttr"
        const val COLUMN_ATTACK_TYPE = "attackType"
        const val COLUMN_ROLES = "roles"
        const val COLUMN_IMG = "img"
        const val COLUMN_ICON = "icon"
        const val COLUMN_BASE_HEALTH = "baseHealth"
        const val COLUMN_BASE_HEALTH_REGEN = "baseHealthRegen"
        const val COLUMN_BASE_MANA = "baseMana"
        const val COLUMN_BASE_MANA_REGEN = "baseManaRegen"
        const val COLUMN_BASE_ARMOR = "baseArmor"
        const val COLUMN_BASE_MR = "baseMr"
        const val COLUMN_BASE_ATTACK_MIN = "baseAttackMin"
        const val COLUMN_BASE_ATTACK_MAX = "baseAttackMax"
        const val COLUMN_BASE_STR = "baseStr"
        const val COLUMN_BASE_AGI = "baseAgi"
        const val COLUMN_BASE_INT = "baseInt"
        const val COLUMN_STR_GAIN = "strGain"
        const val COLUMN_AGI_GAIN = "agiGain"
        const val COLUMN_INT_GAIN = "intGain"
        const val COLUMN_ATTACK_RANGE = "attackRange"
        const val COLUMN_PROJECTILE_SPEED = "projectileSpeed"
        const val COLUMN_ATTACK_RATE = "attackRate"
        const val COLUMN_MOVE_SPEED = "moveSpeed"
        const val COLUMN_TURN_RATE = "turnRate"
        const val COLUMN_CM_ENABLED = "cmEnabled"
        const val COLUMN_LEGS = "legs"
        const val COLUMN_HERO_ID = "heroId"
        const val COLUMN_TURBO_PICKS = "turboPicks"
        const val COLUMN_TURBO_WINS = "turboWins"
        const val COLUMN_PRO_BAN = "proBan"
        const val COLUMN_PRO_WIN = "proWin"
        const val COLUMN_PRO_PICK = "proPick"
        const val COLUMN_1_PICK = "_1Pick"
        const val COLUMN_1_WIN = "_1Win"
        const val COLUMN_2_PICK = "_2Pick"
        const val COLUMN_2_WIN = "_2Win"
        const val COLUMN_3_PICK = "_3Pick"
        const val COLUMN_3_WIN = "_3Win"
        const val COLUMN_4_PICK = "_4Pick"
        const val COLUMN_4_WIN = "_4Win"
        const val COLUMN_5_PICK = "_5Pick"
        const val COLUMN_5_WIN = "_5Win"
        const val COLUMN_6_PICK = "_6Pick"
        const val COLUMN_6_WIN = "_6Win"
        const val COLUMN_7_PICK = "_7Pick"
        const val COLUMN_7_WIN = "_8Pick"
        const val COLUMN_8_PICK = "_8Win"
        const val COLUMN_8_WIN = "_8_win"
        const val COLUMN_NULL_PICK = "nullPick"
        const val COLUMN_NULL_WIN = "nullWin"
        const val TYPE_TEXT = "TEXT"
        const val TYPE_INTEGER = "INTEGER"
        const val TYPE_REAL = "REAL"
        const val DROP_COMMAND = "DROP TABLE IF EXISTS " + HEROES_TABLE_NAME
        const val CREATE_COMMAND =
            "CREATE TABLE IF NOT EXISTS $HEROES_TABLE_NAME ($COLUMN_ID $TYPE_INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME $TYPE_TEXT, " +
                    "$COLUMN_LOCALIZED_NAME $TYPE_TEXT, " +
                    "$COLUMN_PRIMARY_ATTR $TYPE_TEXT, " +
                    "$COLUMN_ATTACK_TYPE $TYPE_TEXT, " +
                    "$COLUMN_ROLES $TYPE_TEXT, " +
                    "$COLUMN_IMG $TYPE_TEXT, " +
                    "$COLUMN_ICON $TYPE_TEXT, " +
                    "$COLUMN_BASE_HEALTH $TYPE_INTEGER, " +
                    "$COLUMN_BASE_HEALTH_REGEN $TYPE_REAL, " +
                    "$COLUMN_BASE_MANA $TYPE_INTEGER, " +
                    "$COLUMN_BASE_MANA_REGEN $TYPE_TEXT, " +
                    "$COLUMN_BASE_ARMOR $TYPE_REAL, " +
                    "$COLUMN_BASE_MR $TYPE_INTEGER, " +
                    "$COLUMN_BASE_ATTACK_MIN $TYPE_INTEGER, " +
                    "$COLUMN_BASE_ATTACK_MAX $TYPE_INTEGER, " +
                    "$COLUMN_BASE_STR $TYPE_INTEGER, " +
                    "$COLUMN_BASE_AGI $TYPE_INTEGER, " +
                    "$COLUMN_BASE_INT $TYPE_INTEGER, " +
                    "$COLUMN_STR_GAIN $TYPE_REAL, " +
                    "$COLUMN_AGI_GAIN $TYPE_REAL, " +
                    "$COLUMN_INT_GAIN $TYPE_REAL, " +
                    "$COLUMN_ATTACK_RANGE $TYPE_INTEGER, " +
                    "$COLUMN_PROJECTILE_SPEED $TYPE_INTEGER, " +
                    "$COLUMN_ATTACK_RATE $TYPE_REAL, " +
                    "$COLUMN_MOVE_SPEED $TYPE_INTEGER, " +
                    "$COLUMN_TURN_RATE $TYPE_REAL, " +
                    "$COLUMN_CM_ENABLED $TYPE_TEXT, " +
                    "$COLUMN_LEGS $TYPE_INTEGER, " +
                    "$COLUMN_HERO_ID $TYPE_INTEGER, " +
                    "$COLUMN_TURBO_PICKS $TYPE_INTEGER, " +
                    "$COLUMN_TURBO_WINS $TYPE_INTEGER, " +
                    "$COLUMN_PRO_BAN $TYPE_INTEGER, " +
                    "$COLUMN_PRO_WIN $TYPE_INTEGER, " +
                    "$COLUMN_PRO_PICK $TYPE_INTEGER, " +
                    "$COLUMN_1_PICK $TYPE_INTEGER, " +
                    "$COLUMN_1_WIN $TYPE_INTEGER, " +
                    "$COLUMN_2_PICK $TYPE_INTEGER, " +
                    "$COLUMN_2_WIN $TYPE_INTEGER, " +
                    "$COLUMN_3_PICK $TYPE_INTEGER, " +
                    "$COLUMN_3_WIN $TYPE_INTEGER, " +
                    "$COLUMN_4_PICK $TYPE_INTEGER, " +
                    "$COLUMN_4_WIN $TYPE_INTEGER, " +
                    "$COLUMN_5_PICK $TYPE_INTEGER, " +
                    "$COLUMN_5_WIN $TYPE_INTEGER, " +
                    "$COLUMN_6_PICK $TYPE_INTEGER, " +
                    "$COLUMN_6_WIN $TYPE_INTEGER, " +
                    "$COLUMN_7_PICK $TYPE_INTEGER, " +
                    "$COLUMN_7_WIN $TYPE_INTEGER, " +
                    "$COLUMN_8_PICK $TYPE_INTEGER, " +
                    "$COLUMN_8_WIN $TYPE_INTEGER, " +
                    "$COLUMN_NULL_PICK $TYPE_INTEGER, " +
                    "$COLUMN_NULL_WIN $TYPE_INTEGER" +
                    ")"
    }
}