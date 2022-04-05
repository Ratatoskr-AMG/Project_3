package ru.ratatoskr.project_3.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.base.EventHandler
import ru.ratatoskr.project_3.domain.helpers.events.ProfileEvent
import ru.ratatoskr.project_3.domain.helpers.states.ProfileState
import ru.ratatoskr.project_3.domain.useCases.user.GetDotaBuffUserUseCase
import ru.ratatoskr.project_3.domain.useCases.user.GetOpenDotaUserUseCase
import ru.ratatoskr.project_3.domain.useCases.user.GetSteamUserUseCase

import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getSteamUserUseCase: GetSteamUserUseCase,
    private val getOpenDotaUserUseCase: GetOpenDotaUserUseCase,
    private val getDotaBuffUserUseCase: GetDotaBuffUserUseCase
) : ViewModel(), EventHandler<ProfileEvent> {

    private val _profile_state: MutableLiveData<ProfileState> =
        MutableLiveData<ProfileState>(ProfileState.IndefinedState)
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

    private fun getResponseFromDotaBuff(steam_user_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var dbResponse = getDotaBuffUserUseCase.getDotabuffResponseOnId(steam_user_id)
            val index = dbResponse.lastIndexOf("https://www.dotabuff.com/players/")
            val part = dbResponse.substring(index)
            val addr = part.substringBefore('"')
            val index2 = addr.lastIndexOf('/')
            var part2 = addr.substring(index2 + 1)

            var odResponse = getOpenDotaUserUseCase.getOpenDotaResponseOnId(part2)
            Log.e("TOHA", "Tier:" + odResponse.rank_tier)
        }
    }

    override fun obtainEvent(event: ProfileEvent) {

        when (val currentState = _profile_state.value) {
            is ProfileState.IndefinedState -> reduce(event)
            is ProfileState.LoggedIntoSteam -> reduce(event)
        }
    }

    private fun reduce(event: ProfileEvent) {

        when (event) {
            is ProfileEvent.OnSteamLogin -> loginWithSteam(event)
            is ProfileEvent.OnSteamExit -> exitSteam()
        }
    }

    private fun loginWithSteam(event: ProfileEvent.OnSteamLogin) {

        val user_id = event.steam_user_id

        viewModelScope.launch(Dispatchers.IO) {

            var steamResponse = getSteamUserUseCase.getSteamResponseOnId(user_id)

            var player = steamResponse.response.players[0]
            player.steamid = user_id

            //addSteamPlayerUseCase.addPlayer(player)

            getResponseFromOpenDota(player.steamid)
            getResponseFromDotaBuff(player.steamid)

            try {
                if (user_id != "") {
                    _profile_state.postValue(
                        ProfileState.LoggedIntoSteam(
                            user_id,
                            player.avatarmedium!!,
                            player.personaname!!
                        )
                    )
                }
            } catch (e: Exception) {
                _profile_state.postValue(ProfileState.ErrorProfileState)
            }

        }

    }

    private fun exitSteam() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _profile_state.postValue(
                    ProfileState.IndefinedState
                )
            } catch (e: Exception) {
                _profile_state.postValue(ProfileState.ErrorProfileState)
            }

        }
    }
}




