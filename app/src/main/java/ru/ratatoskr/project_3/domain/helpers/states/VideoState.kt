package ru.ratatoskr.project_3.domain.helpers.states

import com.google.android.exoplayer2.SimpleExoPlayer

sealed class VideoState {
    object LoadingState : VideoState()
    object ErrorState : VideoState()
    object StopState : VideoState()
    data class PlayerState(
        val player: SimpleExoPlayer,
        val curr_time: Int,
        val curr_addr: String
        ) : VideoState()
}
