package ru.ratatoskr.project_3.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.DefaultEventListener
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import io.ktor.util.cio.*
import ru.ratatoskr.project_3.domain.helpers.events.VideoEvent
import ru.ratatoskr.project_3.domain.helpers.states.VideoState
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.VideoViewModel


@ExperimentalFoundationApi
@Composable
fun VideoScreen(
    viewModel: VideoViewModel,
) {

    val viewState = viewModel.videoState.observeAsState()

    when (val state = viewState.value) {
        is VideoState.PlayerState -> {
            videoPlayer(
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
}


@Composable
fun videoPlayer(
    exoPlayer: SimpleExoPlayer,
    OnPlay: Unit,
    OnPause: Unit,
    OnStop: Unit,
    OnStamp: Unit
) {

    Log.e("TOHA2", "currentPosition:" + exoPlayer.currentPosition)
    Log.e("TOHA2", "playbackState:" + exoPlayer.playbackState)

    val context = LocalContext.current

    exoPlayer.addListener(object : Player.EventListener {
        fun onBtnCLick() {
            Log.e("TOHA", "onBtnCLick")
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying) {
                // Active playback.
                Log.e("TOHA", "isPlaying" + exoPlayer.getCurrentPosition())
            } else {
                // Not playing because playback is paused, ended, suppressed, or the player
                // is buffering, stopped or failed. Check player.getPlayWhenReady,
                // player.getPlaybackState, player.getPlaybackSuppressionReason and
                // player.getPlaybackError for details.

                Log.e("TOHA", "Not playing" + exoPlayer.getCurrentPosition())
            }
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
            Log.e("TOHA", "playbackState" + playbackState)
        }

        override fun onSeekProcessed() {
            super.onSeekProcessed()
            Log.e("TOHA", "onSeekProcessed")
        }

        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
            super.onTimelineChanged(timeline, reason)
            Log.e("TOHA", "onTimelineChanged")
            Log.e("TOHA", "reason: $reason")
        }
    })

    var uri by remember {
        mutableStateOf(
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        )
    }

    val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
        context,
        Util.getUserAgent(context, context.packageName)
    )

    var source = ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(
            Uri.parse(
                uri
            )
        )

    if(exoPlayer.currentPosition>0) {
        exoPlayer.prepare(source, false, false)
    }else{
        exoPlayer.prepare(source)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier
                .padding(bottom = 0.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.DarkGray
                        )
                    )
                )
        )


        Button(onClick = {
            // Elephant Dream by Blender Foundation
            //uri =
            //    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            //exoPlayer.setPlayWhenReady(false)
            // exoPlayer.seekTo(0);
            //exoPlayer.setPlayWhenReady(false);
            Log.e("TOHA", "onClick:" + exoPlayer.currentPosition)
            val time : Double  = exoPlayer.currentPosition.toDouble() / 1000

            Toast.makeText(
                context,
                "Current time (ms): " + time,
                Toast.LENGTH_SHORT
            ).show();

            //exoPlayer.prepare(source)
        }, modifier = Modifier
            .height(40.dp)
            .align(Alignment.BottomCenter)) {
            Text(text = "Get time", modifier = Modifier.fillMaxWidth())
        }



    }

    LaunchedEffect(key1 = Unit, block = {


    })


}

interface MediaPlayback {

    fun playPause()

    fun forward(durationInMillis: Long)

    fun rewind(durationInMillis: Long)
}