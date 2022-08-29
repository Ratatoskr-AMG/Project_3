package ru.ratatoskr.doheco.data.contracts

import android.provider.BaseColumns

class FavoritesContract {
    companion object FavoritesEntries : BaseColumns {
        const val COLUMN_HERO_ID = "heroId"
        const val FAVORITES_TABLE_NAME = "favorites"
    }
}