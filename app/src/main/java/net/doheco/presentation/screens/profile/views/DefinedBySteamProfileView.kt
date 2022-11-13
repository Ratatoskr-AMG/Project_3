package net.doheco.presentation.screens.profile.views

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.domain.utils.rememberForeverLazyListState
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.models.ProfileState
import java.lang.System.exit

@ExperimentalFoundationApi
@Composable
fun DefinedBySteamProfileView(
    state: ProfileState.SteamNameIsDefinedState,
    viewModel: ProfileViewModel,
    player_tier: String,
    updatingDataButtonText: String,
    navController: NavController,
    dialogState: MutableState<Boolean>,
    onSteamExit: () -> Unit,
    onReloadClick: () -> Unit
) {
    Log.e("TOHA","heroes_list_last_modifiedXXXXX:"+updatingDataButtonText)
    var isUpdating = if(updatingDataButtonText=="01/01/1970 03:00:00") true else false

    /*var updateText = if(isUpdating) {
        stringResource(id = R.string.wait)
    }else {
        stringResource(id = R.string.heroes_list_last_modified) + " (" + updatingDataButtonText + ")"
    }
    */

    /*if(updatingDataButtonText=="time"){
        updateText= stringResource(id = R.string.time_block)
    }*/

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
                ProfileHeaderView(navController, state, viewModel, player_tier)
            }
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
                            text = updatingDataButtonText
                        )
                    }

                }
                Divider(color = Color(0xFF0d111c))
            }
        }
    }
}