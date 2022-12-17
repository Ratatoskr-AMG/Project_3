package net.doheco.presentation.screens.profile.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.domain.utils.rememberForeverLazyListState
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.models.ProfileState
import net.doheco.presentation.theme.loadPicture


@ExperimentalFoundationApi
@Composable
fun DefinedBySteamProfileView(
    state: ProfileState.SteamDefinedState,
    viewModel: ProfileViewModel,
    player_tier: String,
    navController: NavController,
    dialogState: MutableState<Boolean>,
    onSteamExit: () -> Unit,
    onReloadClick: () -> Unit
) {
    //Log.e("TOHA","heroes_list_last_modifiedXXXXX:"+updatingDataButtonText)
    //var isUpdating = if(updatingDataButtonText=="01/01/1970 03:00:00") true else false

    /*var updateText = if(isUpdating) {
        stringResource(id = R.string.wait)
    }else {
        stringResource(id = R.string.heroes_list_last_modified) + " (" + updatingDataButtonText + ")"
    }
    */

    /*if(updatingDataButtonText=="time"){
        updateText= stringResource(id = R.string.time_block)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            state = rememberForeverLazyListState(key = "Profile"),
            modifier = Modifier
                .fillMaxSize()
                //.background(Color(0x55202020))
                .background(Color(0x000000))
        ) {

            stickyHeader {
                ProfileHeaderView(
                    navController,
                    state,
                    viewModel,
                    player_tier,
                    onReloadClick,
                    dialogState,
                    true,
                    onSteamExit
                )
            }
            /*
            item {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            //navController.navigate(Screens.Steam.route)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                            .clickable {
                                onSteamExit()
                            }
                    ) {
                        Text(
                            fontSize = 12.sp,
                            color = Color.White,
                            text = stringResource(id = R.string.exit)
                        )
                    }
                }
                Divider(color = Color(0xFF0d111c))
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            dialogState.value = true
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            fontSize = 12.sp,
                            color = Color.White,
                            text = stringResource(id = R.string.send_offer)
                        )
                    }
                }
                Divider(color = Color(0xFF0d111c))
            }
            */
            /*
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            //if(!isUpdating) {
                            onReloadClick()
                            //}
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            fontSize = 12.sp,
                            color = Color.White,
                            text = state.btnText
                        )
                    }

                }
                Divider(color = Color(0xFF0d111c))
            }*/
            if (state.playerMatchesList != null) {
                items(state.playerMatchesList!!) { item ->
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
                                    modifier = Modifier.align(Center)
                                )
                            }
                            Box(modifier = Modifier.width(40.dp)) {

                                Text(
                                    text = item.deaths.toString(),
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.align(Center)
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
                                    modifier = Modifier.align(Center)
                                )
                            }
                        }
                    }


                }
            }
        }
    }*/
}