package ru.ratatoskr.project_3.presentation.screens.account.tiers

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
import javax.inject.Inject

@HiltViewModel
class TiersViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    private val getSteamUserUseCase: GetSteamUserUseCase,
    private val getOpenDotaUserUseCase: GetOpenDotaUserUseCase,
    private val getDotaBuffUserUseCase: GetDotaBuffUserUseCase
) : AndroidViewModel(Application()), EventHandler<ProfileEvent> {

    var appSharedPreferences = appSharedPreferences

    var player_tier = appSharedPreferences.getString("player_tier", "undefined")
    var sp_heroes_list_last_modified =
        appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

    private val _profile_state: MutableLiveData<ProfileState> =
        MutableLiveData<ProfileState>(
            ProfileState.IndefinedState(
                player_tier!!,
                sp_heroes_list_last_modified!!
            )
        )
    val profileState: LiveData<ProfileState> = _profile_state

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
                    _profile_state.postValue(
                        ProfileState.LoggedIntoSteam(
                            player.steamid,
                            player.avatarmedium!!,
                            player.personaname!!,
                            sp_tier,
                            sp_heroes_list_last_modified!!
                        )
                    )
                }
            } catch (e: Exception) {
                _profile_state.postValue(ProfileState.ErrorProfileState)
            }


        }
    }

    override fun obtainEvent(event: ProfileEvent) {
        Log.e("TOHA","obtainEvent")
        when (profileState.value) {
            is ProfileState.IndefinedState -> reduce(event)
            is ProfileState.LoggedIntoSteam -> reduce(event)
        }
    }

    private fun reduce(event: ProfileEvent) {
        Log.e("TOHA","reduce")
        when (event) {
            is ProfileEvent.OnSteamLogin -> loginWithSteam(event)
            is ProfileEvent.OnSteamExit -> exitSteam()
            is ProfileEvent.OnTierChange -> selectTier(event)
        }
    }

    private fun loginWithSteam(event: ProfileEvent.OnSteamLogin) {

        val user_id = event.steam_user_id

        viewModelScope.launch(Dispatchers.IO) {

            var steamResponse = getSteamUserUseCase.getSteamResponseOnId(user_id)

            var player = steamResponse.response.players[0]

            player.steamid = user_id
            getResponseFromOpenDota(player.steamid)
            getResponseFromDotaBuff(player)

        }

    }

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
}




