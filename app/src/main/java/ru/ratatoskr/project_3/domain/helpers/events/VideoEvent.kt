package ru.ratatoskr.project_3.domain.helpers.events

sealed class VideoEvent {
    object OnPlay : VideoEvent()
    data class OnPause(val pause_time: Int) : VideoEvent()
    data class OnStamp(val stamp_time: Int) : VideoEvent()
    data class OnStop(val stop_time: Int) : VideoEvent()
}
