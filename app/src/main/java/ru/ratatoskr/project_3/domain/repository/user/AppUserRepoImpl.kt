package ru.ratatoskr.project_3.domain.repository.user

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.SteamResponse
import ru.ratatoskr.project_3.domain.model.SteamPlayer
import java.lang.Exception
import javax.inject.Inject

class AppUserRepoImpl @Inject constructor(
    private val httpAppClient: HttpClient
) {

    suspend fun getResponseFromSteam(steam_user_id: String): SteamResponse {

        val Url = "http://api.steampowered.com/" +
                "ISteamUser/GetPlayerSummaries/v0002/?" +
                "key=D076D1B0AD4391F8156F8EED08C597CE&" +
                "steamids=" + steam_user_id;

        Log.e("TOHA", "Url:" + Url)

        return try {
            httpAppClient.get(Url)
        } catch (e: Exception) {
            error(e)
        }

    }

}

