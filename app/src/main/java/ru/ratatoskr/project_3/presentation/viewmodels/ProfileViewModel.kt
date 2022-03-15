package ru.ratatoskr.project_3.presentation.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.ReadMe
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.presentation.activity.Screens
import javax.inject.Inject

sealed class ProfileState {
    object TestState : ProfileState()
    object IndefinedState : ProfileState()
    object LoadingState : ProfileState()
    object ErrorProfileState : ProfileState()
    data class LoggedIntoSteam(
        val steam_user_id: Int,
    ) : ProfileState()
}


@HiltViewModel
class ProfileViewModel @Inject constructor(
) : ViewModel() {

    val _profile_state: MutableLiveData<ProfileState> =
        MutableLiveData<ProfileState>(ProfileState.IndefinedState)
    val profile_state: LiveData<ProfileState> = _profile_state

    fun steamLogin(user_id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("TOHA", "Setted!")
                if (user_id > 0) {
                    _profile_state.set(ProfileState.LoggedIntoSteam(user_id))
                }

            } catch (e: Exception) {
                Log.e("TOHA", "Exception: " + e.toString())
                _profile_state.set(ProfileState.ErrorProfileState)
            }
        }


    }


    fun isSteamLoggedSwitch_old(user_id: Int) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                if (user_id > 0) {
                    _profile_state.set(
                        //ProfileState.LoadingState


                        ProfileState.LoggedIntoSteam(user_id)


                    )

                } else {
                    _profile_state.set(
                        ProfileState.IndefinedState
                    )
                }
            } catch (e: Exception) {
                _profile_state.set(ProfileState.ErrorProfileState)
            }

        }

    }


}
