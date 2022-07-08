package ru.ratatoskr.project_3.presentation.screens.account.steam

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.utils.EventHandler
import ru.ratatoskr.project_3.presentation.screens.account.profile.models.ProfileEvent
import ru.ratatoskr.project_3.presentation.screens.account.profile.models.ProfileState
import ru.ratatoskr.project_3.domain.model.steam.SteamPlayer
import ru.ratatoskr.project_3.domain.useCases.user.GetDotaBuffUserUseCase
import ru.ratatoskr.project_3.domain.useCases.user.GetOpenDotaUserUseCase
import ru.ratatoskr.project_3.domain.useCases.user.GetSteamUserUseCase
import ru.ratatoskr.project_3.presentation.screens.account.steam.models.SteamEvent
import ru.ratatoskr.project_3.presentation.screens.account.steam.models.SteamState
import javax.inject.Inject

@HiltViewModel
class SteamViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    private val getSteamUserUseCase: GetSteamUserUseCase,
    private val getOpenDotaUserUseCase: GetOpenDotaUserUseCase,
    private val getDotaBuffUserUseCase: GetDotaBuffUserUseCase
) : AndroidViewModel(Application()), EventHandler<SteamEvent> {

    var appSharedPreferences = appSharedPreferences

    var player_tier = appSharedPreferences.getString("player_tier", "undefined")
    var sp_heroes_list_last_modified =
        appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

    private val _steam_state: MutableLiveData<SteamState> =
        MutableLiveData<SteamState>(
            SteamState.IndefinedState(
                player_tier!!
            )
        )
    val steamState: LiveData<SteamState> = _steam_state

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
                _steam_state.postValue(SteamState.ErrorProfileState)
            }


        }
    }

    override fun obtainEvent(event: SteamEvent) {
        Log.e("TOHA","obtainEvent")
        when (steamState.value) {
            is SteamState.IndefinedState -> reduce(event)
            is SteamState.LoggedIntoSteam -> reduce(event)
        }
    }

    private fun reduce(event: SteamEvent) {
        Log.e("TOHA","reduce")
        when (event) {
            is SteamEvent.OnSteamLogin -> loginWithSteam(event)
            is SteamEvent.OnSteamExit -> exitSteam()
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
/*
    private fun selectTier(event: ProfileEvent.OnTierChange) {
        val selected_tier = event.selected_tier
        viewModelScope.launch(Dispatchers.IO) {

            var state = profileState.value

            appSharedPreferences.edit().putString("player_tier", selected_tier).apply()

            when (state) {
                is ProfileState.IndefinedState -> {
                    try {
                        if (selected_tier != "undefined") {
                            _profile_state.postValue(
                                ProfileState.IndefinedState(
                                    selected_tier,
                                    state.heroes_list_last_modified
                                )
                            )
                            Log.e("TOHA"," _profile_state:"+ _profile_state.value.toString())
                        }
                    } catch (e: Exception) {
                        _profile_state.postValue(ProfileState.ErrorProfileState)
                    }
                }
                is ProfileState.LoggedIntoSteam -> {
                    try {
                        if (selected_tier != "undefined") {
                            _profile_state.postValue(
                                ProfileState.LoggedIntoSteam(
                                    state.steam_user_id,
                                    state.steam_user_avatar,
                                    state.steam_user_name,
                                    selected_tier,
                                    state.heroes_list_last_modified
                                )
                            )
                        }
                    } catch (e: Exception) {
                        _profile_state.postValue(ProfileState.ErrorProfileState)
                    }
                }
            }
        }
    }
*/
    private fun exitSteam() {

        var sp_tier = appSharedPreferences.getString("player_tier", "undefined")
        var sp_heroes_list_last_modified =
            appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _steam_state.postValue(
                    SteamState.IndefinedState()
                )
            } catch (e: Exception) {
                _steam_state.postValue(ProfileState.ErrorProfileState)
            }

        }
    }

}




