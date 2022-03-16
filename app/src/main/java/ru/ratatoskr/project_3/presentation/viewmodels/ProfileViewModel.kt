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
import ru.ratatoskr.project_3.domain.extensions.set
import javax.inject.Inject

sealed class ProfileState {
    object IndefinedState : ProfileState()
    object LoadingState : ProfileState()
    object ErrorProfileState : ProfileState()
    data class LoggedIntoSteam(
        val steam_user_id: String,
    ) : ProfileState()
}

sealed class ProfileEvent {
    data class OnSteamLogin(val steam_user_id: String) : ProfileEvent()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
) : ViewModel(), EventHandler<ProfileEvent> {

    val _profile_state: MutableLiveData<ProfileState> =
        MutableLiveData<ProfileState>(ProfileState.IndefinedState)
    val profile_state: LiveData<ProfileState> = _profile_state

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

    private fun isSteamLoggedSwitch(event:ProfileEvent.OnSteamLogin) {

        val user_id = event.steam_user_id

        Log.e("TOHA", "isSteamLoggedSwitch"+user_id)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (user_id != "") {
                    _profile_state.postValue(
                        ProfileState.LoggedIntoSteam(user_id)
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




