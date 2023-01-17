package net.doheco.presentation.screens.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.utils.EventHandler
import net.doheco.presentation.screens.video.models.VideoEvent
import net.doheco.presentation.screens.video.models.VideoState

import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    player:SimpleExoPlayer
) : ViewModel(), EventHandler<VideoEvent> {

    val player=player
    val url1=""
    val url2=""
    private val _video_state: MutableLiveData<VideoState> =
        MutableLiveData<VideoState>(VideoState.PlayerState(player,0,url1))
    val videoState: LiveData<VideoState> = _video_state

    override fun obtainEvent(event: VideoEvent) {

        when (_video_state.value) {
            is VideoState.PlayerState -> reduce(event)
            else -> {}
        }
    }

    private fun reduce(event: VideoEvent) {

        when (event) {
            is VideoEvent.OnStamp -> registerStamp(event)
            is VideoEvent.OnStop -> stopPlayer(player)
            else -> {}
        }
    }

    private fun stopPlayer(player: SimpleExoPlayer) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                //player.stop()
            } catch (e: Exception) {
              // Log.d("TOHA","_video_state.postValue ex")
            }

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




