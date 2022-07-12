package ru.ratatoskr.project_3.presentation.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.presentation.screens.account.profile.models.ProfileEvent
import ru.ratatoskr.project_3.presentation.screens.account.profile.models.ProfileState
import ru.ratatoskr.project_3.presentation.screens.account.profile.ProfileViewModel
import ru.ratatoskr.project_3.presentation.screens.account.tiers.TiersViewModel
import ru.ratatoskr.project_3.presentation.screens.account.tiers.models.TiersEvent
import ru.ratatoskr.project_3.presentation.screens.account.tiers.models.TiersState

@ExperimentalFoundationApi
@Composable
fun TiersScreen(
    navController: NavController,
    viewState: State<TiersState?>,
    viewModel: TiersViewModel,
    appSharedPreferences: SharedPreferences
) {

    when (val viewState = viewState.value) {
        is TiersState.IndefinedState -> {
            TiersView(navController, viewState, appSharedPreferences) {
                viewModel.obtainEvent(
                    TiersEvent.OnTierChange(it)
                )
            }
        }
        is TiersState.DefinedState -> {
            TiersView(navController, viewState, appSharedPreferences) {
                viewModel.obtainEvent(
                    TiersEvent.OnTierChange(it)
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
    })
}

@ExperimentalFoundationApi
@Composable
fun TiersHeader(
    navController: NavController,
    state: TiersState,
    appSharedPreferences: SharedPreferences
) {
    var tierImage by remember {mutableStateOf("http://ratatoskr.ru/app/img/tier/0.png")}
    var tierDescription = "Tier undefined"
    var tierTitle = "Select your tier"

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
            tierTitle = "Your tier"
        }
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
fun tierRow(
    tierNum: Int,
    viewState: TiersState,
    OnTierChange: (String) -> Unit
) {

    var selectedTier by remember { mutableStateOf("0") }

    when (viewState) {
        is TiersState.IndefinedState -> {
            Log.e("TOHA","viewState.player_tier="+viewState.player_tier)
            if (viewState.player_tier != "undefined") {
                selectedTier = viewState.player_tier[0] + ""
            }
        }
        is TiersState.DefinedState -> {
            if (viewState.player_tier != "undefined") {
                selectedTier = viewState.player_tier[0] + ""
            }
        }
    }

    Log.e("TOHA", "selectedTier:" + selectedTier);

    var tierImage = "http://ratatoskr.ru/app/img/tier/0.png"
    var tierDescription = "Tier undefined"
    var tierName = "Undefined"
    var rowTextColor = Color.White
    var rowBackgroundColor = Color.Transparent

    if (tierNum > 0) {
        tierName = "Tier:" + tierNum
        tierImage = "http://ratatoskr.ru/app/img/tier/$tierNum.png"
        when (tierNum) {
            1 -> tierName = "Herald"
            2 -> tierName = "Guardian"
            3 -> tierName = "Crusader"
            4 -> tierName = "Archon"
            5 -> tierName = "Legend"
            6 -> tierName = "Ancient"
            7 -> tierName = "Divine"
            8 -> tierName = "Immortal"
        }
    }

    if (selectedTier==tierNum.toString()) {
        rowTextColor = Color.Black
        rowBackgroundColor = Color(0xFFc98000)
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
            .background(rowBackgroundColor)
            .clickable { OnTierChange(tierNum.toString()) }

    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                painter = rememberImagePainter(tierImage),
                contentDescription = tierDescription
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                fontSize = 12.sp,
                lineHeight = 50.sp,
                color = rowTextColor,
                text = tierName
            )

        }

    }
}

@ExperimentalFoundationApi
@Composable
fun TiersView(
    navController: NavController,
    viewState: TiersState,
    appSharedPreferences: SharedPreferences,
    onTierChange: (String) -> Unit,
) {
    var scrollState = rememberForeverLazyListState(key = "Tiers")


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
                TiersHeader(navController, viewState, appSharedPreferences)
            }
            for (i in 0..8) {
                item {
                    tierRow(
                        i,
                        viewState,
                        onTierChange
                    )
                }
            }
        }
    }
}
