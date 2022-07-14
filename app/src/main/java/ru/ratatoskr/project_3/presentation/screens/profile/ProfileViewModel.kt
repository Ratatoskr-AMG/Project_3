package ru.ratatoskr.project_3.presentation.screens.profile

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.utils.EventHandler
import ru.ratatoskr.project_3.presentation.screens.profile.models.ProfileEvent
import ru.ratatoskr.project_3.presentation.screens.profile.models.ProfileState
import ru.ratatoskr.project_3.domain.useCases.user.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    private val GetPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase,
    private val GetPlayerSteamNameFromSPUseCase: GetPlayerSteamNameFromSPUseCase,
) : AndroidViewModel(Application()), EventHandler<ProfileEvent> {

    var appSharedPreferences = appSharedPreferences

    var player_tier_from_sp = GetPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    var player_steam_name_from_sp =
        GetPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences)

    var sp_heroes_list_last_modified =
        appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

    private val _profile_state: MutableLiveData<ProfileState> = getInitProfileState()

    fun getPlayerTierFromSP(): String {
        return GetPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    }

    fun getPlayerSteamNameFromSP(): String {
        return GetPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences)
    }

    val profileState: LiveData<ProfileState> = _profile_state

    override fun obtainEvent(event: ProfileEvent) {
        when (profileState.value) {
            is ProfileState.UndefinedState -> reduce(event)
            is ProfileState.SteamNameIsDefinedState -> reduce(event)
        }
    }

    private fun reduce(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnSteamExit -> exitSteam()
        }
    }

    private fun getInitProfileState(): MutableLiveData<ProfileState> {

        var sp_tier = appSharedPreferences.getString("player_tier", "undefined")
        var player_steam_name =
            appSharedPreferences.getString("player_steam_name", "undefined").toString()

        if (player_steam_name != "undefined") {
            return MutableLiveData<ProfileState>(
                ProfileState.SteamNameIsDefinedState(
                    sp_tier!!,
                    player_steam_name,
                    sp_heroes_list_last_modified
                )
            )
        } else {
            return MutableLiveData<ProfileState>(
                ProfileState.UndefinedState(
                    sp_tier!!,
                    sp_heroes_list_last_modified
                )
            )
        }
    }

    fun setSteamIsDefinedProfileState() {
        //Log.e("TOHA", "setSteamIsDefinedProfileState")
        try {
            _profile_state.postValue(
                ProfileState.SteamNameIsDefinedState(
                    player_tier_from_sp,
                    player_steam_name_from_sp,
                    sp_heroes_list_last_modified!!
                )
            )
        } catch (e: Exception) {
            _profile_state.postValue(ProfileState.ErrorProfileState)
        }
    }

    fun setUndefinedProfileState() {
        Log.e("TOHA", "setUndefinedProfileState")
        try {
            _profile_state.postValue(
                ProfileState.UndefinedState(player_tier_from_sp, sp_heroes_list_last_modified!!)
            )
        } catch (e: Exception) {
            _profile_state.postValue(ProfileState.ErrorProfileState)
        }
    }

    private fun exitSteam() {

        var sp_tier = appSharedPreferences.getString("player_tier", "undefined")
        appSharedPreferences.edit().putString("player_steam_name", "undefined")
            .apply();
        var steam_name_now =
            appSharedPreferences.getString("player_steam_name", "undefined").toString()

        Log.e("TOHA", "steam_name_now:" + steam_name_now)

        var sp_heroes_list_last_modified =
            appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _profile_state.postValue(
                    ProfileState.UndefinedState(sp_tier!!, sp_heroes_list_last_modified!!)
                )
            } catch (e: Exception) {
                _profile_state.postValue(ProfileState.ErrorProfileState)
            }
            Log.e("TOHA", "exitSteam")
        }
    }
}




