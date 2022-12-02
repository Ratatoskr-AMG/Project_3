package net.doheco.presentation.screens.profile.views

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
import coil.compose.rememberAsyncImagePainter
import net.doheco.R
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.models.ProfileState

@ExperimentalFoundationApi
@Composable
fun ProfileHeaderView(
    navController: NavController,
    viewState: ProfileState,
    viewModel: ProfileViewModel,
    player_tier: String,
    onReloadClick: () -> Unit,
    dialogState: MutableState<Boolean>,
    logged:Boolean
) {

    var tierImage by remember { mutableStateOf("https://doheco.net/app/img/tier/0.png") }
    var tierDescription = "Tier undefined"
    lateinit var profileTitle: String

    when (viewState) {
        is ProfileState.UndefinedState -> {
            profileTitle = stringResource(id = R.string.title_profile)
        }
        is ProfileState.SteamNameIsDefinedState -> {
            profileTitle = viewModel.getPlayerSteamNameFromSP()
        }
        else -> {}
    }

    if (player_tier != "undefined") {
        tierImage =
            "https://doheco.net/app/img/tier/" + player_tier[0] + ".png"
    }

    tierDescription = player_tier + " tier"

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


        Row(modifier = Modifier.width(210.dp)) {
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .clickable {
                        navController.navigate(Screens.Tier.route)
                    }
                    .border(1.dp, Color(0x880d111c), CircleShape)
                    .clip(CircleShape),
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
                    painter = rememberAsyncImagePainter(tierImage),
                    contentDescription = tierDescription,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color(0x880d111c), CircleShape)
                )
            }

            Box(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .height(70.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    profileTitle,
                    color = Color.White,
                    fontSize = 16.sp,
                    lineHeight = 20.sp
                )

            }
        }

        Box(
            modifier = Modifier.clickable { navController.navigate(Screens.Tier.route) },
            contentAlignment = Alignment.Center

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.padding(end = 10.dp)) {
                    Image(
                        painter = painterResource(R.drawable.ic_synchronize_wh),
                        contentDescription = tierDescription,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .border(1.dp, Color(0x880d111c), CircleShape)
                            .clickable { onReloadClick() }
                    )
                }

                Box(modifier = Modifier.padding(end = 10.dp)) {
                    Image(
                        painter = painterResource(R.drawable.ic_icons8_speech_bubble),
                        contentDescription = tierDescription,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .border(1.dp, Color(0x880d111c), CircleShape)
                            .clickable {
                                dialogState.value = true
                            }
                    )
                }
                if (logged) {
                    Box(modifier = Modifier.padding(start = 5.dp)) {
                        Image(
                            painter = painterResource(R.drawable.ic_logout),
                            contentDescription = tierDescription,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp)
                                .border(1.dp, Color(0x880d111c), CircleShape)
                        )
                    }
                } else {
                    Box(modifier = Modifier) {
                        Image(
                            painter = painterResource(R.drawable.ic_icons8_steam),
                            contentDescription = tierDescription,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .border(1.dp, Color(0x880d111c), CircleShape)
                                .clickable {
                                    navController.navigate(Screens.Steam.route)
                                }
                        )
                    }
                }
            }
        }
    }
}