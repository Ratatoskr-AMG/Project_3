package net.doheco
//package kz.lava.bundle.core.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import kz.lava.bundle.ui.theme.Lava_android_appTheme

fun Modifier.shimmerBackground(shape: Shape = RectangleShape): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000, easing = LinearEasing),
            RepeatMode.Reverse
        ),
    )
    val shimmerColors = listOf(
        Color.Black.copy(alpha = 1f),
        Color.White.copy(alpha = 0.4f),
    )
    var brush2 = Brush.sweepGradient(
        colors = shimmerColors,
        center = Offset(translateAnimation, translateAnimation),
    )
    val brush = Brush.radialGradient(
        colors = shimmerColors,
        center = Offset(translateAnimation, translateAnimation),
        radius = 4000f,
        tileMode = TileMode.Mirror,
    )
    return@composed this.then(background(brush, shape))
}

@Preview(showBackground = true)
@Composable
fun ShimmerPreview () {
    //Lava_android_appTheme() {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                Modifier.size(300.dp, 60.dp)
                    .shimmerBackground(RoundedCornerShape(5.dp))
            )
            Spacer(modifier = Modifier.size(20.dp))
            Box(
                Modifier.size(300.dp, 60.dp)
                    .shimmerBackground(RoundedCornerShape(5.dp))
            )
            Spacer(modifier = Modifier.size(20.dp))
            Box(
                Modifier.size(300.dp, 60.dp)
                    .shimmerBackground(RoundedCornerShape(5.dp))
            )
        }
    //}
}