package ru.ratatoskr.project_3.data.contracts

import android.provider.BaseColumns

class SteamContract {
    companion object UsersSteamEntries : BaseColumns {
        const val USERS_STEAM_TABLE_NAME = "users_steam"
        const val COLUMN_STEAM_ID = "steam_id"
        //const val fetchHeroes = "SELECT * FROM $USERS_STEAM_TABLE_NAME ORDER BY $COLUMN_STEAM_ID ASC"
    }
}