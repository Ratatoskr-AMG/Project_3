package ru.ratatoskr.project_3.presentation.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.events.ProfileEvent
import ru.ratatoskr.project_3.domain.helpers.states.ProfileState
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.ProfileViewModel
import java.util.*

@ExperimentalFoundationApi
@Composable
fun ProfileScreen(
    navController: NavController,
    viewState: State<ProfileState?>,
    viewModel: ProfileViewModel,
    appSharedPreferences: SharedPreferences
) {


    when (val state = viewState.value) {
        is ProfileState.IndefinedState -> {
            UndefinedProfileView(state, navController,appSharedPreferences)
        }
        is ProfileState.LoggedIntoSteam -> {
            definedBySteamProfileView(
                state,
                navController,
                appSharedPreferences
            ) { viewModel.obtainEvent(ProfileEvent.OnSteamExit) }

        }
        is ProfileState.LoadingState -> LoadingView("Profile is loading")
        is ProfileState.ErrorProfileState -> MessageView("Profile error!")
    }
    LaunchedEffect(key1 = Unit, block = {
    })
}

@ExperimentalFoundationApi
@Composable
fun ProfileHeader(
    navController: NavController,
    state: ProfileState,
    appSharedPreferences: SharedPreferences
) {

    var tierImage = "http://ratatoskr.ru/app/img/tier/undefined.png"
    var tierDescription = "Tier undefined"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(Color.Black)
    ) {
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
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 45.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            when (state) {
                is ProfileState.IndefinedState -> {
                    var spTier = appSharedPreferences.getString("player_tier", "undefined").toString()
                    if(spTier!="undefined"){
                        tierImage = "http://ratatoskr.ru/app/img/tier/" + spTier[0] + ".png"
                        tierDescription = "Tier "+spTier[0]
                        Log.e("TOHA","IndefinedState.spTier:"+spTier[0])
                    }
                    Row() {
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
                                    ru.ratatoskr.project_3.R.drawable.ic_back
                                ),
                                contentDescription = "Back"
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(start = 30.dp)
                                .height(70.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                "Profile",
                                color = Color.White,
                                fontSize = 16.sp,
                                lineHeight = 20.sp
                            )

                        }
                    }
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color(0x880d111c), CircleShape)
                            .clickable {
                                //navController.popBackStack()
                            }
                    ) {

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
                is ProfileState.LoggedIntoSteam -> {
                    Log.e("TOHA","state.player_tier:"+state.player_tier)
                    if(state.player_tier!="undefined"){
                        tierImage = "http://ratatoskr.ru/app/img/tier/" + state.player_tier[0] + ".png"
                        tierDescription = state.player_tier[0]+" tier"
                        Log.e("TOHA","LoggedIntoSteam.state_tier:"+state.player_tier[0])
                    }

                    Row(modifier = Modifier.width(160.dp)) {
                        Box(
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                                .border(1.dp, Color(0x880d111c), CircleShape)
                                .clip(CircleShape)
                                .clickable {
                                    navController.popBackStack()
                                },

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
                                .padding(start = 30.dp)
                                .height(70.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(top = 0.dp)
                                    .width(170.dp),
                            ) {
                                Box() {
                                    Text(
                                        state.steam_user_name,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        lineHeight = 20.sp
                                    )
                                }


                            }
                        }
                    }
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier

                            .size(70.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color(0x880d111c), CircleShape)
                            .clickable {
                                //navController.popBackStack()
                            }
                    ) {
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
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun UndefinedProfileView(
    state: ProfileState.IndefinedState,
    navController: NavController,
    appSharedPreferences: SharedPreferences,
) {
    var scrollState = rememberForeverLazyListState(key = "Profile")
    val heroes_list_last_modified = Date(appSharedPreferences.getLong("heroes_list_last_modified", 0))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55202020))
        ) {

            stickyHeader {
                ProfileHeader(navController, state,appSharedPreferences)
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
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
                            text = "Sign in with Steam"
                        )
                    }

                }

            }


            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
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
                        .height(50.dp)
                        .clickable {
                            //navController.navigate(Screens.Steam.route)
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
                            text = "Heroes list last modified: " + heroes_list_last_modified
                        )
                    }

                }

            }

        }
    }
}

@ExperimentalFoundationApi
@Composable
fun definedBySteamProfileView(
    state: ProfileState.LoggedIntoSteam,
    navController: NavController,
    appSharedPreferences: SharedPreferences,
    onSteamExit: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            state = rememberForeverLazyListState(key = "Profile"),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55202020))
        ) {

            stickyHeader {
                ProfileHeader(navController, state,appSharedPreferences)
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
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
                            text = "Exit"
                        )
                    }

                }

            }

            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
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
                        .height(50.dp)
                        .clickable {
                            //navController.navigate(Screens.Steam.route)
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
                            text = "Heroes list last modified: " + Date(appSharedPreferences.getLong("heroes_list_last_modified", 0))
                        )
                    }

                }

            }

        }
    }
}
