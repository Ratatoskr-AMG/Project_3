package ru.ratatoskr.project_3.domain.repository.heroes

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.useCases.calculations.BasicIndicatorsUseCase
import java.lang.Exception
import javax.inject.Inject

class HeroesOpendotaRepoImpl @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getAllHeroesListFromAPI(): List<Hero> {

        val URL = "https://api.opendota.com/api/heroStats/";
        Log.e("TOHA","getAllHeroesListFromAPI")
        return try {

            BasicIndicatorsUseCase().calculate(client.get(URL))

        } catch (e: Exception) {
            error(e)
        }

    }

}

