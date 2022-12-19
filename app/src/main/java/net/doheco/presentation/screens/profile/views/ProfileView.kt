package net.doheco.presentation.screens.profile.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    var logged = false
    var profileTitle = stringResource(id = R.string.title_profile)

    if (viewModel.ifSteamLoged()) {
        logged = true
        profileTitle = viewModel.getPlayerSteamNameFromSP()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ProfileHeaderView(
            navController,
            profileTitle,
            player_tier,
            onReloadClick,
            dialogState,
            logged,
        ) { viewModel.obtainEvent(ProfileEvent.OnSteamExit) }

        when (viewState) {
            is ProfileState.SteamDefinedState -> {
                if (viewState.playerMatchesList != null) {
                    LazyColumn(
                        state = rememberForeverLazyListState(key = "Profile"),
                        modifier = Modifier
                            .fillMaxSize()
                            //.background(Color(0x55202020))
                            .background(Color(0x000000))
                    ) {

                        items(viewState.playerMatchesList!!) { item ->
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
            is ProfileState.APICallResultProfileState -> {
                APICallResultScreenBox(viewModel)
            }
            is ProfileState.UndefinedState -> {
                UndefinedScreenBox(viewState)
            }
            else -> {
                EmptyScreenBox(viewState)
            }
        }
    }
}

@Composable
fun UndefinedScreenBox(viewState: ProfileState) {

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
            text = stringResource(id = R.string.login_offer1),
            color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp
        )
    }
}


@Composable
fun APICallResultScreenBox(viewModel:ProfileViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp).fillMaxHeight().width(200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp),
                textAlign = TextAlign.Center,
                text = "Next update:",
                color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp
            )
            Text(
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp),
                textAlign = TextAlign.Center,
                text = viewModel.state.value,
                color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp
            )
            Button(
                modifier = Modifier.fillMaxWidth().padding(top=20.dp),
                onClick = {
                    viewModel.obtainEvent(ProfileEvent.OnAPICallResultScreenBoxClose)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF00821d),
                    disabledBackgroundColor = Color(0xFF6a36a3),
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                ),

            ){
                Text(text = stringResource(id = R.string.close))
            }
        }
    }
}

@Composable
fun EmptyScreenBox(viewState: ProfileState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

    }

}