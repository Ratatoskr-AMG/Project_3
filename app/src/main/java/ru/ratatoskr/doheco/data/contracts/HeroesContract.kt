package ru.ratatoskr.doheco.data.contracts

import android.provider.BaseColumns

class HeroesContract {
    companion object HeroesEntries : BaseColumns {
        const val databaseApp = "heroes"
        const val COLUMN_LOCALIZED_NAME = "localizedName"
        const val HEROES_TABLE_NAME = "heroes"
        const val fetchHeroes =
            "SELECT * FROM ${ru.ratatoskr.doheco.data.contracts.HeroesContract.HeroesEntries.HEROES_TABLE_NAME} ORDER BY ${ru.ratatoskr.doheco.data.contracts.HeroesContract.HeroesEntries.COLUMN_LOCALIZED_NAME} ASC"
        const val fetchHero = "SELECT * FROM ${ru.ratatoskr.doheco.data.contracts.HeroesContract.HeroesEntries.HEROES_TABLE_NAME} WHERE id = :heroId"

    }
}