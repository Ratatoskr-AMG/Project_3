package ru.ratatoskr.project_3.domain.helpers.states

sealed class VideoState {
    object LoadingState : VideoState()
    object ErrorState : VideoState()
    data class PlayerState(
        val curr_time: Int
    ) : VideoState()
}
