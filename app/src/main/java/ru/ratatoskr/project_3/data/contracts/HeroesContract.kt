package ru.ratatoskr.project_3.data.contracts

import android.provider.BaseColumns

public class HeroesContract {
    companion object HeroesEntries : BaseColumns {
        const val databaseApp = "heroes"
        const val fetch = "SELECT * FROM "+ HeroesContract.HEROES_TABLE_NAME
        const val HEROES_TABLE_NAME = "heroes"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_LOCALIZED_NAME = "localized_name"
        const val COLUMN_PRIMARY_ATTR = "primary_attr"
        const val COLUMN_ATTACK_TYPE = "attack_type"
        const val COLUMN_ROLES = "roles"
        const val COLUMN_IMG = "img"
        const val COLUMN_ICON = "icon"
        const val COLUMN_BASE_HEALTH = "base_health"
        const val COLUMN_BASE_HEALTH_REGEN = "base_health_regen"
        const val COLUMN_BASE_MANA = "base_mana"
        const val COLUMN_BASE_MANA_REGEN = "base_mana_regen"
        const val COLUMN_BASE_ARMOR = "base_armor"
        const val COLUMN_BASE_MR = "base_mr"
        const val COLUMN_BASE_ATTACK_MIN = "base_attack_min"
        const val COLUMN_BASE_ATTACK_MAX = "base_attack_max"
        const val COLUMN_BASE_STR = "base_str"
        const val COLUMN_BASE_AGI = "base_agi"
        const val COLUMN_BASE_INT = "base_int"
        const val COLUMN_STR_GAIN = "str_gain"
        const val COLUMN_AGI_GAIN = "agi_gain"
        const val COLUMN_INT_GAIN = "int_gain"
        const val COLUMN_ATTACK_RANGE = "attack_range"
        const val COLUMN_PROJECTILE_SPEED = "projectile_speed"
        const val COLUMN_ATTACK_RATE = "attack_rate"
        const val COLUMN_MOVE_SPEED = "move_speed"
        const val COLUMN_TURN_RATE = "turn_rate"
        const val COLUMN_CM_ENABLED = "cm_enabled"
        const val COLUMN_LEGS = "legs"
        const val COLUMN_HERO_ID = "hero_id"
        const val COLUMN_TURBO_PICKS = "turbo_picks"
        const val COLUMN_TURBO_WINS = "turbo_wins"
        const val COLUMN_PRO_BAN = "pro_ban"
        const val COLUMN_PRO_WIN = "pro_win"
        const val COLUMN_PRO_PICK = "pro_pick"
        const val COLUMN_1_PICK = "_1_pick"
        const val COLUMN_1_WIN = "_1_win"
        const val COLUMN_2_PICK = "_2_pick"
        const val COLUMN_2_WIN = "_2_win"
        const val COLUMN_3_PICK = "_3_pick"
        const val COLUMN_3_WIN = "_3_win"
        const val COLUMN_4_PICK = "_4_pick"
        const val COLUMN_4_WIN = "_4_win"
        const val COLUMN_5_PICK = "_5_pick"
        const val COLUMN_5_WIN = "_5_win"
        const val COLUMN_6_PICK = "_6_pick"
        const val COLUMN_6_WIN = "_6_win"
        const val COLUMN_7_PICK = "_7_pick"
        const val COLUMN_7_WIN = "_7_win"
        const val COLUMN_8_PICK = "_8_pick"
        const val COLUMN_8_WIN = "_8_win"
        const val COLUMN_NULL_PICK = "null_pick"
        const val COLUMN_NULL_WIN = "null_win"
        const val TYPE_TEXT = "TEXT"
        const val TYPE_INTEGER = "INTEGER"
        const val TYPE_REAL = "REAL"
        const val DROP_COMMAND = "DROP TABLE IF EXISTS "+ HEROES_TABLE_NAME
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