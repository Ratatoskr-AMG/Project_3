package net.doheco.presentation.screens.profile

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
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
        MutableLiveData(ProfileState.EmptyState())
    val profileState: LiveData<ProfileState> = _profileState

    private val state = mutableStateOf("")
    private var coroutineScope = CoroutineScope(Dispatchers.Main)


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
                    updateHeroesAndMatches(true)
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

    private fun updateHeroesAndMatches(isInit:Boolean = false) {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)

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
                    if(openDotaMatchesList!=null){
                        var appDotaMatches = openDotaMatchesList!!.map {
                            var hero = getHeroByIdUseCase.GetHeroById(it.heroId.toString())
                            DotaMatchesConverter.doForward(it, hero)
                        }
                        matchesUseCase.updateMatchesDb(appDotaMatches)
                        Log.e("APICALL", appDotaMatches.toString())
                    }

                    if(isInit) {
                        _profileState.postValue(
                            ProfileState.SteamDefinedState(
                                getPlayerTierFromSP(),
                                getPlayerSteamNameFromSP(),
                                matchesUseCase.getMatchesFromDb(),
                                "Updated",
                            )
                        )
                    }else{
                        state.value = "Updated!"
                        _profileState.postValue(
                            ProfileState.APICallResultProfileState(
                                getPlayerTierFromSP(),
                                getPlayerSteamNameFromSP(),
                                state,
                            )
                        )
                    }
                    Log.e("APICALL", apiCallResult.toString())

                } else {
                    _profileState.postValue(
                        ProfileState.APICallResultProfileState(
                            getPlayerTierFromSP(),
                            getPlayerSteamNameFromSP(),
                            state
                        )
                    )
                    onStart(apiCallResult.remain)
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
            is ProfileEvent.OnAPICallResultScreenBoxClose -> profileInit()
            else -> {}
        }
    }

    private fun onStart(text: String?) {
        if (text == null) {
            Log.e("ERROR", "Remain == null")
        } else {
            val time = text.split(':').toMutableList()
            state.value = "Next update:\n$text"
            coroutineScope.launch {
                while (text != "00:00:00") {
                    delay(1000)
                    when {
                        time[1] == "00" -> {
                            time[1] = "59"
                            time[0] = (time[0].toInt() - 1).toString()
                            if (time[0].length < 2) time[0] = "0" + time[0]
                        }
                        time[2] == "00" -> {
                            time[2] = "59"
                            time[1] = (time[1].toInt() - 1).toString()
                            if (time[1].length < 2) time[1] = "0" + time[1]
                        }
                        time[2] != "00" -> {
                            time[2] = (time[2].toInt() - 1).toString()
                            if (time[2].length < 2) time[2] = "0" + time[2]
                        }
                    }
                    state.value = "Next update:\n${time[0]}:${time[1]}:${time[2]}"
                }
                // Если время кончилось, может добавить какой нибудь текст ?
                state.value = "You can update!"
            }
        }
    }
}




