package ru.ratatoskr.project_3.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import ru.ratatoskr.project_3.presentation.screens.video.models.VideoEvent
import ru.ratatoskr.project_3.presentation.screens.video.models.VideoState
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.screens.video.VideoViewModel
import ru.ratatoskr.project_3.presentation.screens.video.views.videoPlayerView

@ExperimentalFoundationApi
@Composable
fun VideoScreen(
    viewModel: VideoViewModel,
) {

    val viewState = viewModel.videoState.observeAsState()

    when (val state = viewState.value) {
        is VideoState.PlayerState -> {
            videoPlayerView(
                state.player,
                viewModel.obtainEvent(VideoEvent.OnPlay),
                viewModel.obtainEvent(VideoEvent.OnPause(state.curr_time)),
                viewModel.obtainEvent(VideoEvent.OnStop(state.curr_time)),
                viewModel.obtainEvent(VideoEvent.OnStamp(state.curr_time))
            )
        }
        is VideoState.ErrorState -> {
            MessageView("Video Error")
        }
        is VideoState.LoadingState -> LoadingView("Video is loading...")
    }

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            stopPlayer(viewState)
        }
    })
}

fun stopPlayer(state: State<VideoState?>) {
    when (val state = state.value) {
        is VideoState.PlayerState -> {
            state.player.setPlayWhenReady(false);
        }
    }
}