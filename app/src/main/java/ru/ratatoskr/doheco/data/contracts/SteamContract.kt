package ru.ratatoskr.doheco.data.contracts

import android.provider.BaseColumns

class SteamContract {
    companion object UsersSteamEntries : BaseColumns {
        const val USERS_STEAM_TABLE_NAME = "users_steam"
        const val COLUMN_STEAM_ID = "steam_id"
    }
}