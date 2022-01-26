package ru.ratatoskr.project_3.data.storage.contracts

import ru.ratatoskr.project_3.data.HeroesContract

class HeroesSqlContract {

    companion object {
        const val fetch = "SELECT * FROM "+ HeroesContract.HEROES_TABLE_NAME
    }
}