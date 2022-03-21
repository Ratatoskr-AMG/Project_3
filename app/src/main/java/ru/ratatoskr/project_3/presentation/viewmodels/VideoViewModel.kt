package ru.ratatoskr.project_3.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.base.EventHandler
import ru.ratatoskr.project_3.domain.helpers.events.ProfileEvent
import ru.ratatoskr.project_3.domain.helpers.events.VideoEvent
import ru.ratatoskr.project_3.domain.helpers.states.ProfileState
import ru.ratatoskr.project_3.domain.helpers.states.VideoState
import ru.ratatoskr.project_3.domain.useCases.user.GetSteamUserUseCase

import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    player:SimpleExoPlayer
) : ViewModel(), EventHandler<VideoEvent> {

    val url1=""
    val url2=""
    private val _video_state: MutableLiveData<VideoState> =
        MutableLiveData<VideoState>(VideoState.PlayerState(player,0,url1))
    val videoState: LiveData<VideoState> = _video_state

    override fun obtainEvent(event: VideoEvent) {

        when (_video_state.value) {
            is VideoState.PlayerState -> reduce(event)
        }
    }

    private fun reduce(event: VideoEvent) {

        when (event) {
            is VideoEvent.OnStamp -> registerStamp(event)
        }
    }

    private fun registerStamp(event: VideoEvent.OnStamp) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                //_video_state.postValue(
                        //ProfileState.LoggedIntoSteam(user_id,player.avatarmedium!!,player.personaname!!)
                //)
            } catch (e: Exception) {
                //_video_state.postValue(ProfileState.ErrorProfileState)
            }

        }

    }
}




