package net.doheco.presentation.screens.profile

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.R
import net.doheco.domain.useCases.heroes.AddHeroesUserCase
import net.doheco.domain.useCases.heroes.GetAllHeroesFromOpendotaUseCase
import net.doheco.domain.useCases.heroes.GetAllHeroesSortByNameUseCase
import net.doheco.domain.useCases.user.*
import net.doheco.domain.utils.EventHandler
import net.doheco.domain.utils.GetResource
import net.doheco.presentation.screens.profile.models.ProfileEvent
import net.doheco.presentation.screens.profile.models.ProfileState
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    private val getHeroesBaseLastModifiedFromSPUseCase: GetHeroesBaseLastModifiedFromSPUseCase,
    private val getPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase,
    private val getPlayerSteamNameFromSPUseCase: GetPlayerSteamNameFromSPUseCase,
    private val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    private val sendFeedbackUseCase: SendFeedbackUseCase,
    private val getPlayerIdFromSP: GetPlayerIdFromSP,
    private val addHeroesUserCase: AddHeroesUserCase,
    private val getResource: GetResource,
) : AndroidViewModel(Application()), EventHandler<ProfileEvent> {

    var appSharedPreferences = appSharedPreferences

    private val _profileState: MutableLiveData<ProfileState> = getInitProfileState()
    val profileState: LiveData<ProfileState> = _profileState

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
            is ProfileEvent.OnUpdate -> updateHeroesByDefinedUser()
            is ProfileEvent.OnUndefinedProfileUpdate -> updateHeroesByUndefinedUser()
            is ProfileEvent.OnSendFeedback -> sendFeedback(event)
            else -> {}
        }
    }

    private fun getInitProfileState(): MutableLiveData<ProfileState> {

        if (getPlayerSteamNameFromSP() != "undefined") {
            return MutableLiveData<ProfileState>(
                ProfileState.SteamNameIsDefinedState(
                    getPlayerTierFromSP()!!,
                    getPlayerSteamNameFromSP(),
                    getHeroesBaseLastModifiedFromSharedPreferences(),
                    getUpdateBtnText()
                )
            )
        } else {
            return MutableLiveData<ProfileState>(
                ProfileState.UndefinedState(
                    getPlayerTierFromSP()!!,
                    getHeroesBaseLastModifiedFromSharedPreferences(),
                    getUpdateBtnText()
                )
            )
        }
    }

    private fun updateHeroesByUndefinedUser() {

        Log.e("TOHA.1", "updateHeroesByUndefinedUser")

        appSharedPreferences.edit().putString("heroes_update_status", "wait").apply()

        try {
            _profileState.postValue(
                ProfileState.UndefinedState(
                    getPlayerTierFromSP(),
                    getHeroesBaseLastModifiedFromSharedPreferences(),
                    getUpdateBtnText()
                )
            )
        } catch (e: Exception) {
            _profileState.postValue(ProfileState.ErrorProfileState)
        }

        Log.e("TOHA.1", "sp_heroes_list_last_modified:"+getHeroesBaseLastModifiedFromSharedPreferences())
        Log.e("TOHA.1", "curr_time:"+ Date(System.currentTimeMillis()).time)

        var simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val currUpdateDate = appSharedPreferences.getLong("heroes_list_last_modified", 0)
        var formatedDateTime = simpleDateFormat.format(currUpdateDate).toString()
        val oldDate: Date = simpleDateFormat.parse(formatedDateTime)

        var diff = Date(System.currentTimeMillis()).time - oldDate.time
        val seconds: Long = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        if (days < 1) {

            appSharedPreferences.edit().putString("heroes_update_status", "time").apply()
            try {
                _profileState.postValue(
                    ProfileState.UndefinedState(
                        getPlayerTierFromSP(),
                        getHeroesBaseLastModifiedFromSharedPreferences(),
                        getUpdateBtnText()
                    )
                )
            } catch (e: Exception) {
                _profileState.postValue(ProfileState.ErrorProfileState)
            }
            appSharedPreferences.edit().putString("heroes_update_status", "updated").apply()

        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    var heroes =
                        getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi("undefined")
                    if (heroes!!.isEmpty()) {
                        appSharedPreferences.edit().putString("heroes_update_status", "error")
                            .apply()
                        try {
                            _profileState.postValue(
                                ProfileState.UndefinedState(
                                    getPlayerTierFromSP(),
                                    getHeroesBaseLastModifiedFromSharedPreferences(),
                                    getUpdateBtnText()
                                )
                            )
                        } catch (e: Exception) {
                            _profileState.postValue(ProfileState.ErrorProfileState)
                        }
                    } else {
                        var currTime = Date(System.currentTimeMillis()).time
                        addHeroesUserCase.addHeroes(heroes)
                        appSharedPreferences.edit().putLong("heroes_list_last_modified", currTime)
                            .apply()
                        appSharedPreferences.edit().putString("heroes_update_status", "updated")
                            .apply()

                        try {
                            _profileState.postValue(
                                ProfileState.UndefinedState(
                                    getPlayerTierFromSP(),
                                    currTime.toString(),
                                    getUpdateBtnText()
                                )
                            )
                        } catch (e: Exception) {
                            _profileState.postValue(ProfileState.ErrorProfileState)
                        }

                    }
                } catch (e: Exception) {
                    _profileState.postValue(ProfileState.ErrorProfileState)
                }
            }
        }
    }

    private fun updateHeroesByDefinedUser() {

        appSharedPreferences.edit().putString("heroes_update_status", "wait").apply()

        try {
            _profileState.postValue(
                ProfileState.SteamNameIsDefinedState(
                    getPlayerTierFromSP(),
                    getPlayerSteamNameFromSP(),
                    getHeroesBaseLastModifiedFromSharedPreferences(),
                    getUpdateBtnText()
                )
            )
        } catch (e: Exception) {
            _profileState.postValue(ProfileState.ErrorProfileState)
        }

        viewModelScope.launch(Dispatchers.IO) {

            var player_id_from_sp = getPlayerIdFromSP.getPlayerIdFromSP(appSharedPreferences)

            if (player_id_from_sp != "undefined") {
                try {
                    var heroes =
                        getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi(player_id_from_sp)
                    if (heroes!!.isEmpty()) {
                        Log.e("TOHA.2", "isEmpty")
                        appSharedPreferences.edit().putString("heroes_update_status", "time")
                            .apply()
                        try {
                            _profileState.postValue(
                                ProfileState.SteamNameIsDefinedState(
                                    getPlayerTierFromSP(),
                                    getPlayerSteamNameFromSP(),
                                    "time",
                                    getUpdateBtnText()
                                )
                            )

                        } catch (e: Exception) {
                            _profileState.postValue(ProfileState.ErrorProfileState)
                        }
                    } else {
                        Log.e("TOHA.2", "!isEmpty")
                        appSharedPreferences.edit().putString("heroes_update_status", "updated").apply()
                        var currTime = Date(System.currentTimeMillis()).time
                        appSharedPreferences.edit()
                            .putLong("heroes_list_last_modified", currTime)
                            .apply()
                        addHeroesUserCase.addHeroes(heroes)
                            try {
                                _profileState.postValue(
                                    ProfileState.SteamNameIsDefinedState(
                                        getPlayerTierFromSP(),
                                        getPlayerSteamNameFromSP(),
                                        currTime.toString(),
                                        getUpdateBtnText()
                                    )
                                )

                            } catch (e: Exception) {
                                _profileState.postValue(ProfileState.ErrorProfileState)
                            }


                    }

                } catch (e: java.lang.Exception) {
                    Log.e("TOHA", "e:" + e.toString())
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getUpdateBtnText(): String {

        var result = ""

        var updateStatus =
            appSharedPreferences.getString("heroes_update_status", "updated").toString()

        if (updateStatus == "error") {
            result = getResource.getString(id = R.string.error)
        }

        if (updateStatus == "wait") {
            result = getResource.getString(id = R.string.wait)
        }

        if (updateStatus == "time") {
            if (appSharedPreferences.getString("player_steam_name", "undefined").toString() == "undefined") {
                result = getResource.getString(id = R.string.time_block)
            }else{
                result = getResource.getString(id = R.string.time_block2)
            }
        }

        if (updateStatus == "updated") {

            var simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            val currUpdateDate = appSharedPreferences.getLong("heroes_list_last_modified", 0)
            var formatedDateTime = simpleDateFormat.format(currUpdateDate).toString()

            result =
                getResource.getString(id = R.string.heroes_list_last_modified) + " (" + formatedDateTime + ")"
        }

        return result
    }

    private fun sendFeedback(event:ProfileEvent.OnSendFeedback){
        var name = event.name
        var text = event.text
        viewModelScope.launch(Dispatchers.IO) {
            var result = sendFeedbackUseCase.sendFeedbackUseCase(name,text)
            Log.e("TOHA","result:"+result)
        }
    }

    private fun exitSteam() {

        appSharedPreferences.edit().putString("heroes_update_status", "updated").apply()
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
                _profileState.postValue(
                    ProfileState.UndefinedState(
                        sp_tier!!, sp_heroes_list_last_modified!!,
                        getUpdateBtnText()
                    )
                )
            } catch (e: Exception) {
                _profileState.postValue(ProfileState.ErrorProfileState)
            }
            Log.e("TOHA", "exitSteam")
        }
    }

    fun getHeroesBaseLastModifiedFromSharedPreferences(): String {
        return getHeroesBaseLastModifiedFromSPUseCase.getHeroesBaseLastModifiedFromSPUseCase(appSharedPreferences)
    }

    fun getPlayerTierFromSP(): String {
        return getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    }

    fun getPlayerSteamNameFromSP(): String {
        return getPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences)
    }
}




