package net.doheco.presentation.screens.profile

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.useCases.heroes.AddHeroesUserCase
import net.doheco.domain.useCases.heroes.GetAllHeroesFromOpendotaUseCase
import net.doheco.domain.useCases.heroes.GetAllHeroesSortByNameUseCase
import net.doheco.domain.utils.EventHandler
import net.doheco.presentation.screens.profile.models.ProfileEvent
import net.doheco.presentation.screens.profile.models.ProfileState
import net.doheco.domain.useCases.user.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    private val getPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase,
    private val getPlayerSteamNameFromSPUseCase: GetPlayerSteamNameFromSPUseCase,
    private val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    private val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    private val getPlayerIdFromSP: GetPlayerIdFromSP,
    private val addHeroesUserCase: AddHeroesUserCase,
) : AndroidViewModel(Application()), EventHandler<ProfileEvent> {

    var appSharedPreferences = appSharedPreferences

    var player_tier_from_sp = getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    var player_steam_name_from_sp =
        getPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences)

    var sp_heroes_list_last_modified =
        appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

    private val _profile_state: MutableLiveData<ProfileState> = getInitProfileState()

    fun getPlayerTierFromSP(): String {
        return getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    }

    fun getPlayerIdFromSP(): String {
        return getPlayerIdFromSP.getPlayerIdFromSP(appSharedPreferences)
    }

    fun getPlayerSteamNameFromSP(): String {
        return getPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences)
    }

    val profileState: LiveData<ProfileState> = _profile_state

    override fun obtainEvent(event: ProfileEvent) {
        when (profileState.value) {
            is ProfileState.UndefinedState -> reduce(event)
            is ProfileState.SteamNameIsDefinedState -> reduce(event)
            else -> {}
        }
    }

    private fun reduce(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnSteamExit -> exitSteam()
            is ProfileEvent.OnUpdate -> updateHeroes()
            else -> {}
        }
    }

    private fun getInitProfileState(): MutableLiveData<ProfileState> {

        viewModelScope.launch(Dispatchers.IO) {
            //TierHeroes
            val heroes = getAllHeroesSortByNameUseCase.getAllHeroesSortByName()
        }

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

    private fun updateHeroes() {

        viewModelScope.launch(Dispatchers.IO) {

            var player_id_from_sp = getPlayerIdFromSP.getPlayerIdFromSP(appSharedPreferences)
            Log.e("TOHA","player_id_from_sp:"+player_id_from_sp)
            if(player_id_from_sp!="undefined"){
                Log.e("TOHAR","Set Wait");
                appSharedPreferences.edit().putLong("heroes_list_last_modified", 0).apply()
                /*
                if(player_steam_name_from_sp=="undefined"){
                    try {
                        _profile_state.postValue(
                            ProfileState.UndefinedState(player_tier_from_sp, "0")
                        )
                    } catch (e: Exception) {
                        _profile_state.postValue(ProfileState.ErrorProfileState)
                    }
                }
                */
                try {
                    _profile_state.postValue(
                        ProfileState.SteamNameIsDefinedState(
                            player_tier_from_sp,
                            getPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences),
                            "0"
                        )
                    )

                } catch (e: Exception) {
                    _profile_state.postValue(ProfileState.ErrorProfileState)
                }

                Log.e("TOHAR","Now updateHeroes");

                try {
                    var heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi(player_id_from_sp)
                    if(heroes!!.isEmpty()) {
                        Log.e("TOHAR","isEmpty!")
                        //appSharedPreferences.edit().putLong("heroes_list_last_modified", 1).apply()
                        try {
                            _profile_state.postValue(
                                ProfileState.SteamNameIsDefinedState(
                                    player_tier_from_sp,
                                    player_steam_name_from_sp,
                                    "time"
                                )
                            )

                        } catch (e: Exception) {
                            _profile_state.postValue(ProfileState.ErrorProfileState)
                        }
                    }else {
                        Log.e("TOHAR","!isEmpty!")
                        var currTime = Date(System.currentTimeMillis()).time
                        appSharedPreferences.edit().putLong("heroes_list_last_modified", currTime).apply()
                        addHeroesUserCase.addHeroes(heroes)

                        if(player_steam_name_from_sp=="undefined"){
                            try {
                                _profile_state.postValue(
                                    ProfileState.UndefinedState(player_tier_from_sp, currTime.toString())
                                )
                            } catch (e: Exception) {
                                _profile_state.postValue(ProfileState.ErrorProfileState)
                            }
                        }else{
                            try {
                                _profile_state.postValue(
                                    ProfileState.SteamNameIsDefinedState(
                                        player_tier_from_sp,
                                        player_steam_name_from_sp,
                                        currTime.toString()
                                    )
                                )

                            } catch (e: Exception) {
                                _profile_state.postValue(ProfileState.ErrorProfileState)
                            }
                        }

                    }

                } catch (e: java.lang.Exception) {
                    Log.e("TOHA", "e:" + e.toString())
                    e.printStackTrace()
                }
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




