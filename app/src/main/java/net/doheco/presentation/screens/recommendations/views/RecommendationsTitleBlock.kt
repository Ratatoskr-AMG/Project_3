package net.doheco.presentation.screens.recommendations.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.doheco.shimmerBackground

@Composable
fun recommendationsTitleBlock(text: String) {
    Box(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .background(Color(0xFF131313))
    ) {

        Text(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 20.dp,
                    end = 20.dp
                )
                .fillMaxWidth(),
            color = Color.White,
            fontSize = 12.sp,
            text = text
        )
    }
}

@Composable
fun recommendationsTitleBlockLoading() {
    Box(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .shimmerBackground(
                RoundedCornerShape(5.dp)
            )
            .height(35.dp)
    ) {

    }
}


