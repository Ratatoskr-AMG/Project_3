package ru.ratatoskr.doheco.presentation.screens.video.views

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.customviews.stepview.StepList
import com.customviews.stepview.models.StepIndicator
import com.customviews.stepview.models.StepItem
import com.customviews.stepview.models.SubStepItem
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
import ru.ratatoskr.doheco.R

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
                        Color.White,
                        Color.White
                    )
                )
            )
    ) {

        StepList(
            modifier = Modifier.padding(top=20.dp),
            itemList = listOf(
                StepItem(
                    id = 0,
                    name = "Created",
                    description = "CHINA",
                    mark = "13.03.2022",
                    indicator = StepIndicator.Icon(R.drawable.ic_comparing_bl),
                    isVisibleSubStepIndicator = true,
                    subSteps = emptyList(),
                    //enabled = true
                ),
                StepItem(
                    id = 1,
                    name = "Out for delivery",
                    description = "CHINA / Arrived at the Post office",
                    mark = "14.03.2022",
                    indicator = StepIndicator.Icon(R.drawable.ic_comparing_bl),
                    isVisibleSubStepIndicator = true,
                    subSteps = listOf(
                        SubStepItem(
                            name = "Shatian Town",
                            description = "Ready for dispatch",
                            mark = "13.03.2022"
                        ),
                        SubStepItem(
                            name = "Shatian Town",
                            description = "Outbound in sorting center",
                            mark = "13.03.2022"
                        ),
                        SubStepItem(
                            name = "CHINA",
                            description = "Arrived at the Post office",
                            mark = "14.03.2022"
                        ),
                    ),
                    //enabled = true
                )
            ),
            itemTitleStyle = TextStyle(color=Color.White,fontSize = 12.sp),
            itemDescriptionStyle = TextStyle(color=Color.White,fontSize = 12.sp),
            itemMarkStyle = TextStyle(color=Color.White, fontSize = 12.sp)
        )
    /*

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

        */

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