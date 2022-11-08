package net.doheco.presentation.screens.steam

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.utils.EventHandler
import net.doheco.domain.model.steam.SteamPlayer
import net.doheco.domain.useCases.user.*
import net.doheco.presentation.screens.steam.models.SteamEvent
import net.doheco.presentation.screens.steam.models.SteamState
import javax.inject.Inject

@HiltViewModel
class SteamViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    private val getSteamUserUseCase: GetSteamUserUseCase,
    private val getOpenDotaUserUseCase: GetOpenDotaUserUseCase,
    private val getDotaBuffUserUseCase: GetDotaBuffUserUseCase,
    private val getPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase,
    setPlayerTierToSPUseCase: SetPlayerTierToSPUseCase,
    setPlayerSteamNameToSPUseCase: SetPlayerSteamNameToSPUseCase
) : AndroidViewModel(Application()), EventHandler<SteamEvent> {

    var appSharedPreferences = appSharedPreferences
    var setPlayerTierToSPUseCase = setPlayerTierToSPUseCase
    var setPlayerSteamNameToSPUseCase = setPlayerSteamNameToSPUseCase
    private val player_tier_from_sp =
        getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)

    private val _steam_state: MutableLiveData<SteamState> =
        MutableLiveData<SteamState>(
            SteamState.IndefinedState(player_tier_from_sp)
        )

    val steamState: LiveData<SteamState> = _steam_state

    fun getPlayerTierFromSP(): String {
        return getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    }

    private fun getResponseFromOpenDota(steam_user_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var dbResponse = getOpenDotaUserUseCase.getOpenDotaResponseOnId(steam_user_id)

            try {

            } catch (e: Exception) {
                Log.e("TOHA", "getResponseFromOpenDota e:" + e.toString())
            } finally {

            }
        }
    }

    private fun getResponseFromDotaBuff(player: SteamPlayer) {

        viewModelScope.launch(Dispatchers.IO) {

            var dotaBuffResponse = getDotaBuffUserUseCase.getDotabuffResponseOnId(player.steamid)
            val index = dotaBuffResponse.lastIndexOf("https://www.dotabuff.com/players/")
            val part = dotaBuffResponse.substring(index)
            val addr = part.substringBefore('"')
            val index2 = addr.lastIndexOf('/')
            var player_id = addr.substring(index2 + 1)
            var openDotaResponse = getOpenDotaUserUseCase.getOpenDotaResponseOnId(player_id)

            if (openDotaResponse.rank_tier != null) {
                var rank_tier = openDotaResponse.rank_tier.toString()
                appSharedPreferences.edit().putString("player_tier", rank_tier).apply()
                appSharedPreferences.edit().putString("player_id", player_id).apply()

            } else {
                appSharedPreferences.edit().putString("player_tier", "undefined")
                    .apply();
            }

            var sp_tier = appSharedPreferences.getString("player_tier", "undefined").toString()
            var sp_heroes_list_last_modified =
                appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

            try {
                if (player.steamid != "") {

                    setPlayerSteamNameToSPUseCase.setPlayerSteamNameToSP(appSharedPreferences,player.personaname!!)
                    setPlayerTierToSPUseCase.setPlayerTierToSP(appSharedPreferences,sp_tier)

                    _steam_state.postValue(
                        SteamState.LoggedIntoSteam(
                            player.steamid,
                            player.avatarmedium!!,
                            player.personaname!!,
                            sp_tier,
                            sp_heroes_list_last_modified!!
                        )
                    )
                }
            } catch (e: Exception) {
                _steam_state.postValue(SteamState.ErrorSteamState)
            }


        }
    }

    override fun obtainEvent(event: SteamEvent) {
        Log.e("TOHA", "obtainEvent")
        when (steamState.value) {
            is SteamState.IndefinedState -> reduce(event)
            is SteamState.LoggedIntoSteam -> reduce(event)
        }
    }

    private fun reduce(event: SteamEvent) {
        Log.e("TOHA", "reduce")
        when (event) {
            is SteamEvent.OnSteamLogin -> loginWithSteam(event)
        }
    }

    private fun loginWithSteam(event: SteamEvent.OnSteamLogin) {

        val user_id = event.steam_user_id

        viewModelScope.launch(Dispatchers.IO) {

            var steamResponse = getSteamUserUseCase.getSteamResponseOnId(user_id)
            var player = steamResponse.response.players[0]
            player.steamid = user_id
            getResponseFromOpenDota(player.steamid)
            getResponseFromDotaBuff(player)

        }

    }

}




