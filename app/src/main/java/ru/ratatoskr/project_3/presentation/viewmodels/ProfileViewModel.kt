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
import ru.ratatoskr.project_3.domain.useCases.sqlite.user.AddSteamPlayerUseCase
import ru.ratatoskr.project_3.domain.useCases.steam.GetSteamUserUseCase

import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getSteamUserUseCase: GetSteamUserUseCase,
    private val addPlayerToUseCase: AddSteamPlayerUseCase,

    ) : ViewModel(), EventHandler<ProfileEvent> {

    private val _profile_state: MutableLiveData<ProfileState> =
        MutableLiveData<ProfileState>(ProfileState.IndefinedState)
    val profileState: LiveData<ProfileState> = _profile_state

    override fun obtainEvent(event: ProfileEvent) {

        Log.e("TOHA", "obtainEvent")

        when (val currentState = _profile_state.value) {
            is ProfileState.IndefinedState -> reduce(event)
        }
    }

    private fun reduce(event: ProfileEvent) {

        Log.e("TOHA", "reduce")

        when (event) {
            is ProfileEvent.OnSteamLogin -> isSteamLoggedSwitch(event)
        }
    }

    private fun isSteamLoggedSwitch(event: ProfileEvent.OnSteamLogin) {

        val user_id = event.steam_user_id

        Log.e("TOHA", "isSteamLoggedSwitch"+user_id)

        viewModelScope.launch(Dispatchers.IO) {

            var steamRespose = getSteamUserUseCase.getSteamResponseOnId(user_id)
            Log.e("TOHA",steamRespose.toString());
            var player = steamRespose.response.players[0]
            addPlayerToUseCase.addPlayer(player)

            try {
                if (user_id != "") {
                    _profile_state.postValue(
                        ProfileState.LoggedIntoSteam(user_id,player.avatarmedium,player.personaname)

                    )
                } else {
                    _profile_state.postValue(
                        ProfileState.IndefinedState
                    )
                }
            } catch (e: Exception) {
                _profile_state.postValue(ProfileState.ErrorProfileState)
            }

        }

    }
}




