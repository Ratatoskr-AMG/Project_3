package net.doheco.presentation.screens.profile.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
    profileTitle: String,
    player_tier: String,
    onReloadClick: () -> Unit,
    dialogState: MutableState<Boolean>,
    logged:Boolean,
    onSteamExit: () -> Unit
) {

    var tierImage by remember { mutableStateOf("https://doheco.net/app/img/tier/0.png") }
    var tierDescription = "Tier undefined"

    if (player_tier != "undefined") {
        tierImage =
            "https://doheco.net/app/img/tier/" + player_tier[0] + ".png"
    }

    tierDescription = player_tier + " tier"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(Color.Black)
            .padding(start = 20.dp, end = 20.dp, top = 45.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Row(modifier = Modifier.width(210
            .dp)) {
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
                    lineHeight = 20.sp,
                    overflow = TextOverflow.Clip,
                    maxLines = 1
                )
            }
        }

        Box(
            modifier = Modifier.clickable { navController.navigate(Screens.Tier.route) },
            contentAlignment = Alignment.Center

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                /*
                Box(modifier = Modifier.padding(5.dp)) {
                    Image(
                        painter = painterResource(R.drawable.ic_baseline_refresh_24),
                        contentDescription = tierDescription,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                            .border(1.dp, Color(0x880d111c), CircleShape)
                            .clickable { onReloadClick() }
                    )
                }
                */

                Box(modifier = Modifier.padding(5.dp)) {
                    Image(
                        painter = painterResource(R.drawable.ic_baseline_forward_to_inbox_24),
                        contentDescription = tierDescription,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clickable {
                                dialogState.value = true
                            }
                    )
                }
                /*
                if (logged) {
                    Box(modifier = Modifier.padding(5.dp)) {
                        Image(
                            painter = painterResource(R.drawable.ic_baseline_logout_24),
                            contentDescription = tierDescription,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clickable {
                                    onSteamExit()
                                },
                        )
                    }
                } else {
                    Box(modifier = Modifier.padding(5.dp)) {
                        Image(
                            painter = painterResource(R.drawable.ic_icons8_steam),
                            contentDescription = tierDescription,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                                .clickable {
                                    navController.navigate(Screens.Steam.route)
                                }
                        )
                    }
                }

                 */
            }
        }
    }
    Divider(color = Color(0xFF0d111c))
}
