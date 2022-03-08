package ru.ratatoskr.project_3.data.contracts

import android.provider.BaseColumns

class FavoritesContract {
    companion object FavoritesEntries : BaseColumns {
        const val databaseApp = "heroes"
        const val TYPE_TEXT = "TEXT"
        const val TYPE_INTEGER = "INTEGER"
        const val TYPE_REAL = "REAL"
        const val COLUMN_ID = "id"
        const val COLUMN_HERO_ID = "heroId"
        const val FAVORITES_TABLE_NAME = "favorites"
        const val fetchFavorites =
            "SELECT * FROM $FAVORITES_TABLE_NAME ORDER BY $COLUMN_ID DESC"
        const val DROP_COMMAND = "DROP TABLE IF EXISTS $FAVORITES_TABLE_NAME"
        const val CREATE_COMMAND =
            "CREATE TABLE IF NOT EXISTS $FAVORITES_TABLE_NAME ($COLUMN_ID $TYPE_INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_HERO_ID $TYPE_INTEGER, " +
                    ")"
    }
}