package ru.ratatoskr.project_3.presentation.screens.account.profile

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
import ru.ratatoskr.project_3.domain.useCases.user.*
import ru.ratatoskr.project_3.presentation.screens.account.steam.models.SteamEvent
import ru.ratatoskr.project_3.presentation.screens.account.tiers.models.TiersEvent
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    private val getSteamUserUseCase: GetSteamUserUseCase,
    private val getOpenDotaUserUseCase: GetOpenDotaUserUseCase,
    private val getDotaBuffUserUseCase: GetDotaBuffUserUseCase,
    private val GetPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase,
    private val GetPlayerSteamNameFromSPUseCase: GetPlayerSteamNameFromSPUseCase,
) : AndroidViewModel(Application()), EventHandler<ProfileEvent> {

    var appSharedPreferences = appSharedPreferences

    //var player_steam_name = appSharedPreferences.getString("player_steam_name", "undefined").toString()
    var player_tier_from_sp = GetPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    var player_steam_name_from_sp =
        GetPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences)

    var sp_heroes_list_last_modified =
        appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()


    private val _profile_state: MutableLiveData<ProfileState> = getInitProfileState()

    /*
            MutableLiveData<ProfileState>(
                ProfileState.UndefinedState(
                    player_tier_from_sp,
                    sp_heroes_list_last_modified!!
                )
            )
    */
    fun getPlayerTierFromSP(): String {
        return GetPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    }

    fun getPlayerSteamNameFromSP(): String {
        return GetPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences)
    }

    val profileState: LiveData<ProfileState> = _profile_state

    private fun getResponseFromOpenDota(steam_user_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var player_tier = GetPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
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
                    // Log.e("TOHA", "player.personaname!!" + player.personaname!!)
                    // Log.e("TOHA", "player_tier" + sp_tier)
                    // Log.e("TOHA", "sp_heroes_list_last_modified" + sp_heroes_list_last_modified)
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
        //Log.e("TOHA", "obtainEvent")
        //Log.e("TOHA", "profileState.value:" + profileState.value)
        when (profileState.value) {
            is ProfileState.UndefinedState -> reduce(event)
            is ProfileState.SteamNameIsDefinedState -> reduce(event)
        }
    }

    private fun reduce(event: ProfileEvent) {
        //Log.e("TOHA", "reduce:" + event.toString())
        when (event) {
            // is ProfileEvent.OnSteamLogin -> loginWithSteam(event)
            is ProfileEvent.OnSteamExit -> exitSteam()
            //is ProfileEvent.OnTierChange -> selectTier(event)
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

    private fun setSteamStateIfSteamNameIsDefined() {

        viewModelScope.launch(Dispatchers.IO) {
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




