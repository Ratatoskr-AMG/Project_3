package net.doheco.domain.repository

import io.ktor.client.*
import io.ktor.client.request.*
import net.doheco.domain.model.HeroesAndMatchesApiCallResult
import javax.inject.Inject

class DohecoServerRepoImpl @Inject constructor(
    private val client: HttpClient,
) {

    suspend fun getHeroesAndMatches(steamId: String): HeroesAndMatchesApiCallResult {

        try {
            val apiCallResult: HeroesAndMatchesApiCallResult = client.get("https://doheco.net/api/heroStats") {
                url {
                    parameters.append("id", steamId)
                    parameters.append("r", (1000000..9999999999).random().toString())
                }
            }

            /*
val URL =
            "https://doheco.net/api/heroStats/?id=" + steamId + "&r=" + (1000000..9999999999).random();
            client.get("https://ktor.io") {
                url {
                    parameters.append("token", "abc123")
                }
            }
            */

            return apiCallResult
        } catch (e: Exception) {
            error(e)
            return HeroesAndMatchesApiCallResult()
        }
     }
 }