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
import net.doheco.data.converters.DotaMatchesConverter
import net.doheco.domain.useCases.heroes.AddHeroesUserCase
import net.doheco.domain.useCases.heroes.GetAllHeroesFromOpendotaUseCase
import net.doheco.domain.useCases.heroes.GetHeroByIdUseCase
import net.doheco.domain.useCases.system.ServerApiCallUseCase
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
    private val getHeroByIdUseCase: GetHeroByIdUseCase,
    private val UUIdSPUseCase: UUIdSPUseCase,
    private val serverApiCallUseCase: ServerApiCallUseCase
) : AndroidViewModel(Application()), EventHandler<ProfileEvent> {

    private val _profileState: MutableLiveData<ProfileState> =
        MutableLiveData(ProfileState.UndefinedState())
    val profileState: LiveData<ProfileState> = _profileState

    init {
        profileInit()
    }

    fun ifSteamLoged(): Boolean {
        var UUId = UUIdSPUseCase.GetSteamUUIdFromSP(appSharedPreferences)
        return !UUId.isEmpty()
    }

    private fun profileInit() {
        var UUId = UUIdSPUseCase.GetSteamUUIdFromSP(appSharedPreferences)

        if (UUId.isEmpty()) {
            viewModelScope.launch {
                _profileState.postValue(
                    ProfileState.UndefinedState(
                        getPlayerTierFromSP(),
                        "",
                    )
                )
            }
        } else {
            viewModelScope.launch {
                if (matchesUseCase.getMatchesFromDb().isEmpty()) {
                    updateHeroesAndMatches()
                } else {
                    _profileState.postValue(
                        ProfileState.SteamDefinedState(
                            getPlayerTierFromSP(),
                            getPlayerSteamNameFromSP(),
                            matchesUseCase.getMatchesFromDb(),
                            "Welcome",
                        )
                    )
                }
            }
        }
    }

    private fun updateHeroesAndMatches() {

        viewModelScope.launch(Dispatchers.IO) {
            var UUId = UUIdSPUseCase.GetSteamUUIdFromSP(appSharedPreferences)
            if (UUId.isEmpty()) {
                UUId = UUIdSPUseCase.GetAppUUIdFromSP(appSharedPreferences)
                Log.e("APICALL", "GetAppUUIdFromSP:" + UUId)
            }else{
                Log.e("APICALL", "GetSteamUUIdFromSP:" + UUId)
            }
            try {

                val apiCallResult = serverApiCallUseCase.getHeroesAndMatches(UUId)

                Log.e("APICALL", "UUId:" + UUId)
                Log.e("APICALL", "result:" + apiCallResult.result)
                Log.e("APICALL", "matches:" + apiCallResult.matches)
                Log.e("APICALL", "heroes:" + apiCallResult.heroes)
                Log.e("APICALL", "nextUpDate:" + apiCallResult.nextUpDate)
                Log.e("APICALL", "lastUpDate:" + apiCallResult.lastUpDate)

                if (apiCallResult.result == true) {

                    val openDotaMatchesList = apiCallResult.matches
                    Log.e("APICALL", "openDotaMatchesList:" + openDotaMatchesList.toString())
                    var appDotaMatches = openDotaMatchesList!!.map {
                        var hero = getHeroByIdUseCase.GetHeroById(it.heroId.toString())
                        DotaMatchesConverter.doForward(it, hero)
                    }

                    matchesUseCase.updateMatchesDb(appDotaMatches)

                    _profileState.postValue(
                        ProfileState.SteamDefinedState(
                            getPlayerTierFromSP(),
                            getPlayerSteamNameFromSP(),
                            matchesUseCase.getMatchesFromDb(),
                            "Updated",
                        )
                    )
                    Log.e("APICALL", apiCallResult.toString())
                    Log.e("APICALL", appDotaMatches.toString())


                } else {
                    _profileState.postValue(
                        ProfileState.APICallResultProfileState(
                            getPlayerTierFromSP(),
                            getPlayerSteamNameFromSP(),
                            "Next Update in " + apiCallResult.remain
                        )
                    )
                }

            } catch (e: Exception) {
                Log.e("APICALL", e.toString())
                _profileState.postValue(ProfileState.ErrorProfileState(e.toString()))

            }
        }


    }

    private fun sendFeedback(event: ProfileEvent.OnSendFeedback) {
        val name = event.name
        val text = event.text
        viewModelScope.launch(Dispatchers.IO) {
            val result = sendFeedbackUseCase.sendFeedbackUseCase(name, text)
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

        var UUId = UUID.randomUUID().toString()
        UUIdSPUseCase.SetSteamUUIdToSP(appSharedPreferences, "")

        Log.e("TOHA", "steam_name_now:$steamNameNow")

        val spHeroesListLastModified =
            appSharedPreferences.getLong("heroes_list_last_modified", 0).toString()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _profileState.postValue(
                    ProfileState.UndefinedState(
                        spTier!!
                    )
                )
            } catch (e: Exception) {
                _profileState.postValue(ProfileState.ErrorProfileState(e.toString()))
            }
            Log.e("TOHA", "exitSteam")
        }
    }

    fun getPlayerTierFromSP(): String {
        return getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    }

    fun getPlayerSteamNameFromSP(): String {
        return getPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences)
    }

    override fun obtainEvent(event: ProfileEvent) {
        reduce(event)
        /*when (profileState.value) {
            is ProfileState.UndefinedState -> reduce(event)
            is ProfileState.SteamNameIsDefinedState -> reduce(event)
            else -> {}
        }
        */
    }

    private fun reduce(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnSteamExit -> exitSteam()
            is ProfileEvent.OnUpdate -> updateHeroesAndMatches()
            is ProfileEvent.OnSendFeedback -> sendFeedback(event)
            else -> {}
        }
    }


}




