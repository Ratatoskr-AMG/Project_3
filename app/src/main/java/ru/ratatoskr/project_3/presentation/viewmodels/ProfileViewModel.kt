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
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.ReadMe
import ru.ratatoskr.project_3.domain.useCases.sqlite.DropHeroFromFavorites
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetHeroByIdUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetIfHeroIsFavoriteUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.InsertHeroesUseCase
import javax.inject.Inject

sealed class ProfileState {
    object IndefinedState : ProfileState()
    object LoadingState : ProfileState()
    object ErrorProfileState : ProfileState()
    data class LoggedIntoSteam(
        val steam_user_id: Int,
    ) : ProfileState()
}

sealed class ProfileEvent {
    data class SteamIdReceived(val steam_user_id: Int) : ProfileEvent()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(

) : ViewModel() {

    val _profile_state: MutableLiveData<ProfileState> =
        MutableLiveData<ProfileState>(ProfileState.IndefinedState)
    val profile_state: LiveData<ProfileState> = _profile_state

    fun isSteamLoggedSwitch(user_id: Int) {

        viewModelScope.launch {

            try {
                if (user_id > 0) {
                    _profile_state.postValue(
                        ProfileState.LoadingState

                        /*
                        ProfileState.LoadingState(user_id)
                         */

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