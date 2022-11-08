package net.doheco.presentation.screens.comparing.models

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ComparingRow(
    textLeft: String,
    textRight: String,
    textCenterRes: Int
) {
    val configuration = LocalConfiguration.current
    var blockHeight = 40.dp
    val screenWidth = configuration.screenWidthDp
    val valueLeft = textLeft.toFloat()
    val valueRight = textRight.toFloat()
    val summ = valueLeft + valueRight

    //Log.e("TOHAcalc textLeft: ", textLeft)

    val raz = summ / screenWidth

    var leftBlockWidthFl = valueLeft / raz
    var rightBlockWidthFl = valueRight / raz

    if (summ == 0f) {
        leftBlockWidthFl = screenWidth / 2.toFloat()
        rightBlockWidthFl = screenWidth / 2.toFloat()
    }  else {
        if (leftBlockWidthFl == 0f) {
            leftBlockWidthFl = 8f
            rightBlockWidthFl -= 8f
        }
        if (rightBlockWidthFl == 0f) {
            rightBlockWidthFl = 8f
            leftBlockWidthFl -= 8f
        }
    }

    var leftBlockWidth = leftBlockWidthFl.dp
    var rightBlockWidth = rightBlockWidthFl.dp
    var leftBlockColor = Color(0xFF016D19)
    var rightBlockColor = Color(0xFF633397)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(blockHeight)
                    .background(leftBlockColor)
                    .width(leftBlockWidth)
                    .border(2.dp, Color.Black)
            )
            Box(
                modifier = Modifier
                    .height(blockHeight)
                    .background(rightBlockColor)
                    .width(rightBlockWidth)
                    .border(2.dp, Color.Black)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(blockHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = textLeft,
                color = Color.White,
                textAlign = TextAlign.Left,
                fontSize = 12.sp
            )
            Box(/*modifier = Modifier
                .background(Color.Black.copy(alpha = 1f))*/
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 3.dp, bottom = 3.dp),
                    text = stringResource(textCenterRes),
                    textAlign = TextAlign.Center, color = Color.White,
                    fontSize = 12.sp
                )
            }
            Text(
                modifier = Modifier.padding(end = 20.dp),
                text = textRight,
                color = Color.White,
                textAlign = TextAlign.Right,
                fontSize = 12.sp
            )
        }
    }
}