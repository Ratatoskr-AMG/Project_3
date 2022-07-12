package ru.ratatoskr.project_3.domain.repository

import android.content.SharedPreferences
import io.ktor.client.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.domain.model.OpenDotaResponse
import ru.ratatoskr.project_3.domain.model.steam.SteamResponse
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class AppUserRepoImpl @Inject constructor(
    private val httpAppClient: HttpClient
) {

    fun getPlayerTierFromSP(appSharedPreferences: SharedPreferences):String{
        return appSharedPreferences.getString("player_tier", "undefined").toString()
    }

    fun setPlayerTierToSP(appSharedPreferences: SharedPreferences, tier:String){
        appSharedPreferences.edit().putString("player_tier",tier)
            .apply();
    }

    fun getPlayerSteamNameFromSP(appSharedPreferences: SharedPreferences):String{
        return appSharedPreferences.getString("player_steam_name", "undefined").toString()
    }

    fun setPlayerSteamNameToSP(appSharedPreferences: SharedPreferences,name:String){
        appSharedPreferences.edit().putString("player_steam_name",name)
            .apply();
    }

    suspend fun getResponseFromOpenDota(steam_user_id: String): OpenDotaResponse {
        val Url = "https://api.opendota.com/api/players/$steam_user_id";

        return try {
            httpAppClient.get(Url)
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun getResponseFromSteam(steam_user_id: String): SteamResponse {

        val Url = "http://api.steampowered.com/" +
                "ISteamUser/GetPlayerSummaries/v0002/?" +
                "key=D076D1B0AD4391F8156F8EED08C597CE&" +
                "steamids=" + steam_user_id;

        return try {
            httpAppClient.get(Url)
        } catch (e: Exception) {
            error(e)
        }

    }

    fun getResponseFromDotaBuff(steam_user_id: String): String {

        val url = URL("https://www.dotabuff.com/players/"+ steam_user_id)
        val urlConnection = url.openConnection() as HttpURLConnection

        try {
            return urlConnection.inputStream.bufferedReader().readText()
        }  catch (e: Exception) {
            return "e"+e.toString()
        }
        finally {
            urlConnection.disconnect()
        }


    }

}

