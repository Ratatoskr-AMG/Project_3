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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.doheco.R
import net.doheco.domain.useCases.heroes.AddHeroesUserCase
import net.doheco.domain.useCases.heroes.GetAllHeroesFromOpendotaUseCase
import net.doheco.domain.useCases.heroes.GetHeroByIdUseCase
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
    private var appSharedPreferences: SharedPreferences,
    private val getHeroesBaseLastModifiedFromSPUseCase: GetHeroesBaseLastModifiedFromSPUseCase,
    private val getPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase,
    private val getPlayerSteamNameFromSPUseCase: GetPlayerSteamNameFromSPUseCase,
    private val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    private val sendFeedbackUseCase: SendFeedbackUseCase,
    private val getPlayerIdFromSP: GetPlayerIdFromSP,
    private val addHeroesUserCase: AddHeroesUserCase,
    private val getResource: GetResource,
    private val matchesUseCase: MatchesUseCase,
    private val getHeroByIduseCase: GetHeroByIdUseCase
) : AndroidViewModel(Application()), EventHandler<ProfileEvent> {


   // private val _profileState = MutableStateFlow(ProfileState.UndefinedState())

    private val _profileState: MutableLiveData<ProfileState> = MutableLiveData()
    val profileState: LiveData<ProfileState> = _profileState

    override fun obtainEvent(event: ProfileEvent) {
        when (profileState.value) {
            is ProfileState.UndefinedState -> reduce(event)
            is ProfileState.SteamNameIsDefinedState -> reduce(event)
            else -> {}
        }
    }

    init {
        getInitProfileState()
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

    private fun getInitProfileState() {
        viewModelScope.launch {
            val matches = matchesUseCase.getFromDb()
            if (getPlayerSteamNameFromSP() != "undefined") {
                _profileState.postValue(
                    ProfileState.SteamNameIsDefinedState(
                        getPlayerTierFromSP()!!,
                        getPlayerSteamNameFromSP(),
                        getHeroesBaseLastModifiedFromSP(),
                        getUpdateBtnText(),
                        matches
                    )
                )
            } else {
                _profileState.postValue(
                    ProfileState.UndefinedState(
                        getPlayerTierFromSP()!!,
                        getHeroesBaseLastModifiedFromSP(),
                        getUpdateBtnText()
                    )
                )
            }
        }
    }

    private fun updateHeroesByUndefinedUser() {

        Log.e("TOHA.1", "updateHeroesByUndefinedUser")

        appSharedPreferences.edit().putString("heroes_update_status", "wait").apply()

        try {
            _profileState.postValue(
                ProfileState.UndefinedState(
                    getPlayerTierFromSP(),
                    getHeroesBaseLastModifiedFromSP(),
                    getUpdateBtnText()
                )
            )
        } catch (e: Exception) {
            _profileState.postValue(ProfileState.ErrorProfileState)
        }

        Log.e("TOHA.1", "sp_heroes_list_last_modified:"+getHeroesBaseLastModifiedFromSP())
        Log.e("TOHA.1", "curr_time:"+ Date(System.currentTimeMillis()).time)

        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val currUpdateDate = appSharedPreferences.getLong("heroes_list_last_modified", 0)
        val formatedDateTime = simpleDateFormat.format(currUpdateDate).toString()
        val oldDate: Date = simpleDateFormat.parse(formatedDateTime)

        val diff = Date(System.currentTimeMillis()).time - oldDate.time
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
                        getHeroesBaseLastModifiedFromSP(),
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
                    val heroes =
                        getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi("undefined")
                    if (heroes.isEmpty()) {
                        appSharedPreferences.edit().putString("heroes_update_status", "error")
                            .apply()
                        try {
                            _profileState.postValue(
                                ProfileState.UndefinedState(
                                    getPlayerTierFromSP(),
                                    getHeroesBaseLastModifiedFromSP(),
                                    getUpdateBtnText(),
                                )
                            )
                        } catch (e: Exception) {
                            _profileState.postValue(ProfileState.ErrorProfileState)
                        }
                    } else {
                        val currTime = Date(System.currentTimeMillis()).time
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
                    getHeroesBaseLastModifiedFromSP(),
                    getUpdateBtnText()
                )
            )
        } catch (e: Exception) {
            _profileState.postValue(ProfileState.ErrorProfileState)
        }

        viewModelScope.launch(Dispatchers.IO) {

            val playerIdFromSp = getPlayerIdFromSP.getPlayerIdFromSP(appSharedPreferences)

            if (playerIdFromSp != "undefined") {
                try {
                    Log.e("RUSLAN", "Player id: $playerIdFromSp")
                    val matches = matchesUseCase.getMatches(playerIdFromSp ,getHeroByIduseCase)
                    matchesUseCase.updateFromDb(matches)
                    val matchesFromDb = matchesUseCase.getFromDb()
                    val heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi(playerIdFromSp)
                    if (heroes.isEmpty()) {
                        Log.e("TOHA.2", "isEmpty")
                        appSharedPreferences.edit().putString("heroes_update_status", "time")
                            .apply()
                        try {
                            _profileState.postValue(
                                ProfileState.SteamNameIsDefinedState(
                                    getPlayerTierFromSP(),
                                    getPlayerSteamNameFromSP(),
                                    "time",
                                    getUpdateBtnText(),
                                    matchesFromDb
                                )
                            )
                        } catch (e: Exception) {
                            _profileState.postValue(ProfileState.ErrorProfileState)
                        }
                    } else {
                        Log.e("TOHA.2", "!isEmpty")
                        appSharedPreferences.edit().putString("heroes_update_status", "updated").apply()
                        val currTime = Date(System.currentTimeMillis()).time
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
                                        getUpdateBtnText(),
                                        matches
                                    )
                                )
                            } catch (e: Exception) {
                                _profileState.postValue(ProfileState.ErrorProfileState)
                            }
                    }

                } catch (e: java.lang.Exception) {
                    Log.e("TOHA", "e:$e")
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getUpdateBtnText(): String {

        var result = ""

        val updateStatus =
            appSharedPreferences.getString("heroes_update_status", "updated").toString()

        if (updateStatus == "error") {
            result = getResource.getString(id = R.string.error)
        }

        if (updateStatus == "wait") {
            result = getResource.getString(id = R.string.wait)
        }

        if (updateStatus == "time") {
            result = if (appSharedPreferences.getString("player_steam_name", "undefined").toString() == "undefined") {
                getResource.getString(id = R.string.time_block)
            }else{
                getResource.getString(id = R.string.time_block2)
            }
        }

        if (updateStatus == "updated") {

            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            val currUpdateDate = appSharedPreferences.getLong("heroes_list_last_modified", 0)
            val formatedDateTime = simpleDateFormat.format(currUpdateDate).toString()

            result = getResource.getString(id = R.string.heroes_list_last_modified) + " (" + formatedDateTime + ")"
        }

        return result
    }

    private fun sendFeedback(event:ProfileEvent.OnSendFeedback){
        val name = event.name
        val text = event.text
        viewModelScope.launch(Dispatchers.IO) {
            val result = sendFeedbackUseCase.sendFeedbackUseCase(name,text)
            Log.e("TOHA", "result:$result")
        }
    }

    private fun exitSteam() {

        appSharedPreferences.edit().putString("heroes_update_status", "updated").apply()
        val spTier = appSharedPreferences.getString("player_tier", "undefined")
        appSharedPreferences.edit().putString("player_steam_name", "undefined")
            .apply();
        val steamNameNow =
            appSharedPreferences.getString("player_steam_name", "undefined").toString()

        Log.e("TOHA", "steam_name_now:$steamNameNow")

        val spHeroesListLastModified =
            appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _profileState.postValue(
                    ProfileState.UndefinedState(
                        spTier!!, spHeroesListLastModified!!,
                        getUpdateBtnText()
                    )
                )
            } catch (e: Exception) {
                _profileState.postValue(ProfileState.ErrorProfileState)
            }
            Log.e("TOHA", "exitSteam")
        }
    }

    private fun getHeroesBaseLastModifiedFromSP(): String {
        return getHeroesBaseLastModifiedFromSPUseCase.getHeroesBaseLastModifiedFromSPUseCase(appSharedPreferences)
    }

    fun getPlayerTierFromSP(): String {
        return getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    }

    fun getPlayerSteamNameFromSP(): String {
        return getPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences)
    }
}




