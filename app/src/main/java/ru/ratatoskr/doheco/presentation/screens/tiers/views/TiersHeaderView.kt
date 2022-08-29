package ru.ratatoskr.doheco.presentation.screens.tiers.views

import android.content.SharedPreferences
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.presentation.screens.tiers.models.TiersState

@ExperimentalFoundationApi
@Composable
fun TiersHeaderView(
    navController: NavController,
    state: TiersState,
    appSharedPreferences: SharedPreferences
) {
    var tierImage by remember { mutableStateOf("http://ratatoskr.ru/app/img/tier/0.png") }
    var tierDescription = "Tier undefined"
    var tierTitle = stringResource(id = R.string.title_tiers)

    when (state) {
        is TiersState.IndefinedState -> {
            if (state.player_tier != "undefined") {
                tierImage =
                    "http://ratatoskr.ru/app/img/tier/" + state.player_tier[0] + ".png"
                tierDescription = state.player_tier[0] + " tier"
            }
        }
        is TiersState.DefinedState -> {
            if (state.player_tier != "undefined") {
                tierImage =
                    "http://ratatoskr.ru/app/img/tier/" + state.player_tier[0] + ".png"
                tierDescription = state.player_tier[0] + " tier"
            }
        }
        else -> {}
    }

    Row(
        modifier = Modifier
            .drawWithContent {
                drawContent()
                clipRect { // Not needed if you do not care about painting half stroke outside
                    val strokeWidth = Stroke.DefaultMiter
                    val y = size.height // strokeWidth
                    drawLine(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF0d111c),
                                Color(0xFF0d111c),
                                Color(0xFF0d111c),
                                //Color(0xFF000022),
                                //Color(0xFF000022)
                            )
                        ),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Square,
                        start = Offset.Zero.copy(y = y),
                        end = Offset(x = size.width, y = y)
                    )
                }
            }
            .fillMaxWidth()
            .height(140.dp)
            .background(Color.Black)
            .padding(start = 20.dp, end = 20.dp, top = 45.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {


        Row(modifier = Modifier.width(240.dp)) {
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .clickable {
                        navController.popBackStack()
                    }
                    .border(1.dp, Color(0x880d111c), CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(

                    modifier = Modifier
                        .width(15.dp)
                        .height(15.dp),
                    painter = rememberImagePainter(
                        R.drawable.ic_back
                    ),
                    contentDescription = "Back"
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .height(70.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    tierTitle,
                    color = Color.White,
                    fontSize = 16.sp,
                    lineHeight = 20.sp
                )

            }
        }
        Box(
            contentAlignment = Alignment.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                //.padding(top = 7.dp, start = 7.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_comparing_gr),
                    contentDescription = null,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }

            Image(
                painter = rememberImagePainter(tierImage),
                contentDescription = tierDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0x880d111c), CircleShape)
            )

        }

    }
}