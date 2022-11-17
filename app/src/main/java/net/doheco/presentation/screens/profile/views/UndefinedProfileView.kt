package net.doheco.presentation.screens.profile.views


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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.domain.utils.rememberForeverLazyListState
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.models.ProfileState


@ExperimentalFoundationApi
@Composable
fun UndefinedProfileView(
    state: ProfileState.UndefinedState,
    viewModel: ProfileViewModel,
    navController: NavController,
    player_tier: String,
    dialogState: MutableState<Boolean>,
    onReloadClick: () -> Unit
) {

    val scrollState = rememberForeverLazyListState(key = "Profile")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            state = scrollState,
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
                            navController.navigate(Screens.Steam.route)
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
                            text = stringResource(id = R.string.sign_in_with_steam)
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
                Divider(color = Color(0xFF0d111c), modifier = Modifier.padding(end = 20.dp, start = 20.dp))
            }
            item {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                        .clickable { onReloadClick() },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        fontSize = 12.sp,
                        color = Color.White,
                        //text = stringResource(id = R.string.update_time) + " " + heroesListLastModified
                        //text = updatingDataButtonText
                        text = state.btnText
                    )
                }
                Divider(color = Color(0xFF0d111c))
            }
            item {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        fontSize = 12.sp,
                        color = Color(0x99FFFFFF),
                        text = stringResource(id = R.string.login_offer)
                    )
                }
            }

        }
    }
}
