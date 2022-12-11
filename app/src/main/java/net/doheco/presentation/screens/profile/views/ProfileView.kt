package net.doheco.presentation.screens.profile.views

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.domain.utils.rememberForeverLazyListState
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.models.ProfileEvent
import net.doheco.presentation.screens.profile.models.ProfileState
import net.doheco.presentation.theme.loadPicture

@ExperimentalFoundationApi
@Composable
fun ProfileView(
    viewState: ProfileState,
    viewModel: ProfileViewModel,
    navController: NavController,
    player_tier: String,
    dialogState: MutableState<Boolean>,
    onReloadClick: () -> Unit
) {
    var logged=false

    when (viewState) {
        is ProfileState.SteamNameIsDefinedState -> {
            logged=true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ProfileHeaderView(
            navController,
            viewState,
            viewModel,
            player_tier,
            onReloadClick,
            dialogState,
            logged,
        ) {viewModel.obtainEvent(ProfileEvent.OnSteamExit)}

        when (viewState) {

            is ProfileState.SteamNameIsDefinedState -> {
                Log.e("TOHA",viewState.matchesList.toString())
                if (viewState.matchesList != null) {

                    LazyColumn(
                        state = rememberForeverLazyListState(key = "Profile"),
                        modifier = Modifier
                            .fillMaxSize()
                            //.background(Color(0x55202020))
                            .background(Color(0x000000))
                    ) {

                    items(viewState.matchesList!!) { item ->
                        var bgColor = Color(0xFF7E0000)
                        if (item.radiantWin!! && item.playerSlot!! <= 127) {
                            bgColor = Color(0xFF007E00)
                        }
                        if (!item.radiantWin && item.playerSlot!! > 127) {
                            bgColor = Color(0xFF007E00)
                        }
                        Row(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {


                                Image(
                                    contentDescription = item.matchId.toString(),
                                    painter = loadPicture(
                                        url = item.heroIcon,
                                        placeholder = painterResource(id = R.drawable.ic_comparing_gr)
                                    ),
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(35.dp)
                                        .padding(top = 5.dp, bottom = 5.dp, end = 15.dp)

                                )

                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(bgColor)
                                )

                                Text(
                                    text = item.startTimeFormatted,
                                    color = Color.White,
                                    modifier = Modifier.padding(start = 15.dp)
                                )
                                Text(
                                    text = item.durationFormatted,
                                    color = Color.White,
                                    modifier = Modifier.padding(start = 15.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(modifier = Modifier.width(40.dp)) {

                                    Text(
                                        text = item.kills.toString(),
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                                Box(modifier = Modifier.width(40.dp)) {

                                    Text(
                                        text = item.deaths.toString(),
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                                Box(
                                    modifier = Modifier.width(40.dp),
                                    contentAlignment = Alignment.TopCenter
                                ) {

                                    Text(
                                        text = item.assists.toString(),
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }


                    }
                    }
                }
            }
            else -> {
                screenBox(viewState)
            }
        }



    }
}

@Composable
fun screenBox(viewState:ProfileState){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 50.dp, end = 50.dp),
            textAlign = TextAlign.Center,
            //text = stringResource(id = R.string.login_offer),
            text = viewState.toString(),
            color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp
        )
    }
}