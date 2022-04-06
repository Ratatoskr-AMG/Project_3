package ru.ratatoskr.project_3.data.contracts

import android.provider.BaseColumns

class HeroesContract {
    companion object HeroesEntries : BaseColumns {
        const val databaseApp = "heroes"
        const val COLUMN_LOCALIZED_NAME = "localizedName"
        const val HEROES_TABLE_NAME = "heroes"
        const val fetchHeroes =
            "SELECT * FROM $HEROES_TABLE_NAME ORDER BY $COLUMN_LOCALIZED_NAME ASC"
        const val fetchHero = "SELECT * FROM $HEROES_TABLE_NAME WHERE id = :heroId"

    }
}