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
    private var appSharedPreferences: SharedPreferences,
    private val getSteamUserUseCase: GetSteamUserUseCase,
    private val getOpenDotaUserUseCase: GetOpenDotaUserUseCase,
    private val getDotaBuffUserUseCase: GetDotaBuffUserUseCase,
    private val getPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase,
    private var setPlayerTierToSPUseCase: SetPlayerTierToSPUseCase,
    private var setPlayerSteamNameToSPUseCase: SetPlayerSteamNameToSPUseCase,
    private var UUIdSPUseCase: UUIdSPUseCase
) : AndroidViewModel(Application()), EventHandler<SteamEvent> {

    private val playerTierFromSp =
        getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)

    private val _steamState: MutableLiveData<SteamState> =
        MutableLiveData<SteamState>(
            SteamState.IndefinedState(playerTierFromSp)
        )

    val steamState: LiveData<SteamState> = _steamState

    fun getPlayerTierFromSP(): String {
        return getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    }

    private fun getResponseFromOpenDota(steam_user_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var dbResponse = getOpenDotaUserUseCase.getOpenDotaResponseOnId(steam_user_id)
            try {

            } catch (e: Exception) {
                Log.e("TOHA", "getResponseFromOpenDota e:$e")
            } finally {

            }
        }
    }

    private fun getResponseFromDotaBuff(player: SteamPlayer) {

        viewModelScope.launch(Dispatchers.IO) {

            val dotaBuffResponse = getDotaBuffUserUseCase.getDotabuffResponseOnId(player.steamid)
            val index = dotaBuffResponse.lastIndexOf("https://www.dotabuff.com/players/")
            val part = dotaBuffResponse.substring(index)
            val addr = part.substringBefore('"')
            val index2 = addr.lastIndexOf('/')
            val steamPlayerId = addr.substring(index2 + 1)
            val openDotaResponse = getOpenDotaUserUseCase.getOpenDotaResponseOnId(steamPlayerId)
            val rankTier = openDotaResponse.rank_tier
            appSharedPreferences.edit().putString("player_tier", rankTier).apply()

            try {
                if (player.steamid.isNotBlank()) {

                    setPlayerSteamNameToSPUseCase.setPlayerSteamNameToSP(appSharedPreferences,player.personaname!!)
                    setPlayerTierToSPUseCase.setPlayerTierToSP(appSharedPreferences,rankTier)
                    UUIdSPUseCase.SetSteamUUIdToSP(appSharedPreferences,steamPlayerId)

                    _steamState.postValue(
                        SteamState.LoggedIntoSteam(
                            steamPlayerId,
                            player.avatarmedium!!,
                            player.personaname!!,
                            rankTier
                        )
                    )
                }
            } catch (e: Exception) {
                _steamState.postValue(SteamState.ErrorSteamState)
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

        val userId = event.steam_user_id

        viewModelScope.launch(Dispatchers.IO) {
            val steamResponse = getSteamUserUseCase.getSteamResponseOnId(userId)
            val player = steamResponse.response.players[0]
            player.steamid = userId
            getResponseFromOpenDota(player.steamid)
            getResponseFromDotaBuff(player)
        }
    }
}




