/*
Многопоточность
Параллельность
Асинхронность
3 основных ошибки конкурентности
16.6 мс
В скоуп передается диспетчер, это SuperVisorJob
*/

package ru.ratatoskr.project_3.domain.repository.user

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.SteamResponse
import ru.ratatoskr.project_3.domain.model.SteamResponseUser
import ru.ratatoskr.project_3.domain.model.SteamPlayer
import java.lang.Exception
import javax.inject.Inject

class UserSteamRepoImpl @Inject constructor(
    private val roomAppDatabase: RoomAppDatabase,
    private val client: HttpClient
) {

    suspend fun getResponseFromSteam(steam_user_id: String): SteamResponse {

        val Url = "http://api.steampowered.com/" +
                "ISteamUser/GetPlayerSummaries/v0002/?" +
                "key=D076D1B0AD4391F8156F8EED08C597CE&" +
                "steamids=" + steam_user_id;

        Log.e("TOHA", "Url:" + Url)

        return try {
            client.get(Url)
        } catch (e: Exception) {
            error(e)
        }

    }

    suspend fun addPlayer(player: SteamPlayer) {

        try {
            roomAppDatabase.SteamUsersDao().insertPlayer(player)
        } catch (e: Exception) {
            Log.e("TOHA", "updateSqliteTable e: " + e.message.toString())
        } finally {
            Log.e("TOHA", "updateSqliteTable")
        }

    }
}

