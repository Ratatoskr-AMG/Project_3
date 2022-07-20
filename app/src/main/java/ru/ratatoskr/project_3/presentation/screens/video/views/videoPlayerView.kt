package ru.ratatoskr.project_3.presentation.screens.video.views

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util

@Composable
fun videoPlayerView(
    exoPlayer: SimpleExoPlayer,
    OnPlay: Unit,
    OnPause: Unit,
    OnStop: Unit,
    OnStamp: Unit
) {
    val context = LocalContext.current
    var uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"

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

            if (playbackState == ExoPlayer.STATE_IDLE) {
                // exoPlayer.setPlayWhenReady(true);

            }

            if (playbackState == ExoPlayer.STATE_READY) {
                // exoPlayer.setPlayWhenReady(true);
            }

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

    if (exoPlayer.currentPosition > 0) {
        exoPlayer.prepare(source, false, false)
    } else {
        exoPlayer.prepare(source)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Black
                    )
                )
            )
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier
                .padding(bottom = 0.dp)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Black
                        )
                    )
                )
        )

        /*
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
  */


    }

    LaunchedEffect(key1 = Unit, block = {


    })


}