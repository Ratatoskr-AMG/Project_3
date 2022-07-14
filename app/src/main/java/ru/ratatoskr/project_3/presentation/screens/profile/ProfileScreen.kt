package ru.ratatoskr.project_3.presentation.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.presentation.screens.profile.models.ProfileEvent
import ru.ratatoskr.project_3.presentation.screens.profile.models.ProfileState
import ru.ratatoskr.project_3.presentation.screens.profile.ProfileViewModel
import ru.ratatoskr.project_3.presentation.screens.home.rememberForeverLazyListState
import java.util.*

@ExperimentalFoundationApi
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    appSharedPreferences: SharedPreferences
) {

    val player_tier = viewModel.getPlayerTierFromSP()
    val player_steam_name = viewModel.getPlayerSteamNameFromSP()

    if(player_steam_name!="undefined"){
        viewModel.setSteamIsDefinedProfileState()
    }else{
        viewModel.setUndefinedProfileState()
    }

    val viewState = viewModel.profileState.observeAsState()

    val heroes_list_last_modified =
        Date(appSharedPreferences.getLong("heroes_list_last_modified", 0))
            .toString()

    Log.e("TOHA", "player_steam_name:" + player_steam_name)
    Log.e("TOHA", "viewState.value:" + viewState.value)



    when (val state = viewState.value) {
        is ProfileState.UndefinedState -> {

            UndefinedProfileView(state, viewModel, navController, player_tier, heroes_list_last_modified)

        }
        is ProfileState.SteamNameIsDefinedState -> {
            DefinedBySteamProfileView(
                state,
                viewModel,
                player_tier,
                heroes_list_last_modified,
                navController,
            ) { viewModel.obtainEvent(ProfileEvent.OnSteamExit) }

        }
    }

    LaunchedEffect(key1 = Unit, block = {

        // viewModel.player_steam_name

    })
}

@ExperimentalFoundationApi
@Composable
fun ProfileHeader(
    navController: NavController,
    viewState: ProfileState,
    viewModel: ProfileViewModel,
    player_tier: String,
) {

    var tierImage by remember { mutableStateOf("http://ratatoskr.ru/app/img/tier/0.png") }
    var tierDescription = "Tier undefined"
    lateinit var profileTitle : String

    when (viewState) {
        is ProfileState.UndefinedState -> {
            profileTitle = "Profile"
        }
        is ProfileState.SteamNameIsDefinedState -> {
            profileTitle = viewModel.getPlayerSteamNameFromSP()
        }
    }

    tierImage =
        "http://ratatoskr.ru/app/img/tier/" + player_tier[0] + ".png"

    tierDescription = player_tier + " tier"

    /*
    when (state) {
        is ProfileState.IndefinedState -> {
            if (player_tier != "undefined") {
                tierImage =
                    "http://ratatoskr.ru/app/img/tier/" + player_tier + ".png"
                tierDescription = player_tier + " tier"
            }
        }
        is ProfileState.LoggedIntoSteam -> {
            if (player_tier != "undefined") {
                tierImage =
                    "http://ratatoskr.ru/app/img/tier/" + player_tier + ".png"
                tierDescription = player_tier + " tier"
            }
            profileTitle = state.steam_user_name
        }
    }
    */

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

@ExperimentalFoundationApi
@Composable
fun UndefinedProfileView(
    state: ProfileState.UndefinedState,
    viewModel: ProfileViewModel,
    navController: NavController,
    player_tier: String,
    heroes_list_last_modified: String,
) {
    var scrollState = rememberForeverLazyListState(key = "Profile")

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
                ProfileHeader(navController, state, viewModel, player_tier)
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

            /*
            item{
                topPicks()
            }

            */
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun topPicks() {
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
    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                fontSize = 12.sp,
                color = Color.White,
                text = "Top selected"
            )
        }

    }

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
    ) {


        var roles: List<String> =
            listOf("Carry", "Nuker", "Disabler", "Durable", "Support", "Escape")

        LazyRow(
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize(1F)
                .padding(start = 15.dp, end = 15.dp)
        ) {
            // display items horizontally
            items(roles.size) { item ->
                Box(
                    modifier = Modifier
                        .padding(start = 0.dp, end = 30.dp)
                ) {
                    Text(
                        fontSize = 12.sp,
                        color = Color.White,
                        text = roles[item]
                    )
                }
            }
        }


    }
}

@ExperimentalFoundationApi
@Composable
fun DefinedBySteamProfileView(
    state: ProfileState.SteamNameIsDefinedState,
    viewModel: ProfileViewModel,
    player_tier: String,
    heroes_list_last_modified: String,
    navController: NavController,
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
                ProfileHeader(navController, state, viewModel, player_tier)
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
                            text = "Heroes list last modified: " + heroes_list_last_modified
                        )
                    }

                }

            }

        }
    }
}
