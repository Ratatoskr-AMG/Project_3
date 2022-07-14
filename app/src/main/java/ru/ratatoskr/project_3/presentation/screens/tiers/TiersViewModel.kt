package ru.ratatoskr.project_3.presentation.screens.tiers

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.utils.EventHandler
import ru.ratatoskr.project_3.domain.useCases.user.GetDotaBuffUserUseCase
import ru.ratatoskr.project_3.domain.useCases.user.GetOpenDotaUserUseCase
import ru.ratatoskr.project_3.domain.useCases.user.GetPlayerTierFromSPUseCase
import ru.ratatoskr.project_3.domain.useCases.user.GetSteamUserUseCase
import ru.ratatoskr.project_3.presentation.screens.tiers.models.TiersEvent
import ru.ratatoskr.project_3.presentation.screens.tiers.models.TiersState
import javax.inject.Inject

@HiltViewModel
class TiersViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    private val getSteamUserUseCase: GetSteamUserUseCase,
    private val getOpenDotaUserUseCase: GetOpenDotaUserUseCase,
    private val getDotaBuffUserUseCase: GetDotaBuffUserUseCase,
    getPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase
) : AndroidViewModel(Application()), EventHandler<TiersEvent> {

    var appSharedPreferences = appSharedPreferences

    private val player_tier_from_sp =
        getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)

    private val _tiers_state: MutableLiveData<TiersState> =
        MutableLiveData<TiersState>(
            TiersState.IndefinedState(
                player_tier_from_sp!!,
            )
        )
    val tiersState: LiveData<TiersState> = _tiers_state

    /*
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
    */
    /*
    private fun getResponseFromDotaBuff(player: SteamPlayer) {

        viewModelScope.launch(Dispatchers.IO) {

            var dotaBuffResponse = getDotaBuffUserUseCase.getDotabuffResponseOnId(player.steamid)
            val index = dotaBuffResponse.lastIndexOf("https://www.dotabuff.com/players/")
            val part = dotaBuffResponse.substring(index)
            val addr = part.substringBefore('"')
            val index2 = addr.lastIndexOf('/')
            var part2 = addr.substring(index2 + 1)
            var openDotaResponse = getOpenDotaUserUseCase.getOpenDotaResponseOnId(part2)

            if (openDotaResponse.rank_tier != null) {
                appSharedPreferences.edit().putString("player_tier", openDotaResponse.rank_tier)
                    .apply();
            } else {
                appSharedPreferences.edit().putString("player_tier", "undefined")
                    .apply();
            }

            var sp_tier = appSharedPreferences.getString("player_tier", "undefined").toString()
            var sp_heroes_list_last_modified =
                appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

            try {
                if (player.steamid != "") {
                    Log.e("TOHA", "player.personaname!!" + player.personaname!!)
                    Log.e("TOHA", "player_tier" + sp_tier)
                    Log.e("TOHA", "sp_heroes_list_last_modified" + sp_heroes_list_last_modified)
                    _tiers_state.postValue(
                        TiersState.LoggedIntoSteam(
                            player.steamid,
                            player.avatarmedium!!,
                            player.personaname!!,
                            sp_tier,
                            sp_heroes_list_last_modified!!
                        )
                    )
                }
            } catch (e: Exception) {
                _tiers_state.postValue(ProfileState.ErrorProfileState)
            }


        }
    }
*/

    override fun obtainEvent(event: TiersEvent) {
        Log.e("TOHA","obtainEvent")
        when (tiersState.value) {
            is TiersState.IndefinedState -> reduce(event)
            is TiersState.DefinedState -> reduce(event)
        }
    }

    private fun reduce(event: TiersEvent) {
        Log.e("TOHA","reduce")
        when (event) {
            //is TiersEvent.OnSteamLogin -> loginWithSteam(event)
            //is TiersEvent.OnSteamExit -> exitSteam()
            is TiersEvent.OnTierChange -> selectTier(event)
        }
    }

/*
    private fun loginWithSteam(event: TiersEvent.OnSteamLogin) {

        val user_id = event.steam_user_id

        viewModelScope.launch(Dispatchers.IO) {

            var steamResponse = getSteamUserUseCase.getSteamResponseOnId(user_id)

            var player = steamResponse.response.players[0]

            player.steamid = user_id
            getResponseFromOpenDota(player.steamid)
            getResponseFromDotaBuff(player)

        }

    }
*/

    private fun selectTier(event: TiersEvent.OnTierChange) {
        val selected_tier = event.selected_tier
        viewModelScope.launch(Dispatchers.IO) {

            var state = tiersState.value

            appSharedPreferences.edit().putString("player_tier", selected_tier).apply()
            var curr_tier = appSharedPreferences.getString("player_tier", "undefined").toString()
            Log.d("TOHA","curr_tier:"+curr_tier);

            when (state) {
                is TiersState.IndefinedState -> {
                    try {
                        if (selected_tier != "undefined") {
                            _tiers_state.postValue(
                                TiersState.IndefinedState(
                                    selected_tier
                                )
                            )
                            Log.e("TOHA"," _tiers_state:"+ _tiers_state.value.toString())
                        }
                    } catch (e: Exception) {
                        _tiers_state.postValue(TiersState.ErrorTiersState)
                    }
                }
                is TiersState.DefinedState -> {
                    try {
                        if (selected_tier != "undefined") {
                            _tiers_state.postValue(
                                TiersState.DefinedState(
                                    selected_tier,
                                )
                            )
                        }
                    } catch (e: Exception) {
                        _tiers_state.postValue(TiersState.ErrorTiersState)
                    }
                }
            }
        }
    }

/*
    private fun exitSteam() {

        var sp_tier = appSharedPreferences.getString("player_tier", "undefined")
        var sp_heroes_list_last_modified =
            appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _profile_state.postValue(
                    ProfileState.IndefinedState(sp_tier!!, sp_heroes_list_last_modified!!)
                )
            } catch (e: Exception) {
                _profile_state.postValue(ProfileState.ErrorProfileState)
            }

        }
    }
    */

}




