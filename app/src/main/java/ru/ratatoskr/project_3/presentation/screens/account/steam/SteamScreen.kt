package ru.ratatoskr.project_3.presentation.screens

import android.content.SharedPreferences
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.presentation.screens.account.steam.models.SteamEvent
import ru.ratatoskr.project_3.presentation.theme.BGBox
import ru.ratatoskr.project_3.presentation.screens.account.steam.SteamViewModel
import ru.ratatoskr.project_3.presentation.screens.account.steam.models.SteamState

@ExperimentalFoundationApi
@Composable
fun SteamScreen(
    navController: NavController,
    viewState: State<SteamState?>,
    viewModel: SteamViewModel,
    appSharedPreferences: SharedPreferences,
) {
    //var player_tier = appSharedPreferences.getString("player_tier", "undefined").toString()
    //viewModel.getPlayerTierFromSP()

    var player_tier = viewModel.getPlayerTierFromSP()
    when (val state = viewState.value) {
        is SteamState.IndefinedState -> {
            SteamSignInView(navController, state, player_tier) {
                viewModel.obtainEvent(SteamEvent.OnSteamLogin(it))
            }
        }
        is SteamState.LoggedIntoSteam -> {
            SteamLoggedInView(navController, state, state.player_tier)
        }
    }
    LaunchedEffect(key1 = Unit, block = {
    })
}

@ExperimentalFoundationApi
@Composable
fun SteamLoggedInView(
    navController: NavController,
    state: SteamState,
    player_tier:String
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
                SteamHeader(navController, state, player_tier)
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
                            text = "Welcome"
                        )
                    }

                }

            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun SteamSignInView(
    navController: NavController,
    state:SteamState,
    player_tier: String,
    onAuthorizeChange: (String) -> Unit
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
                SteamHeader(navController, state, player_tier)
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
                        .fillMaxSize()
                        .clickable {
                            //navController.navigate(Screens.Attr.route + "/" + column + "/" + hero.id)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 0.dp, end = 0.dp)
                            .fillMaxSize()
                    ) {
                        val REALM = "ratatoskr.ru"
                        val url = "https://steamcommunity.com/openid/login?" +
                                "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
                                "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
                                "openid.mode=checkid_setup&" +
                                "openid.ns=http://specs.openid.net/auth/2.0&" +
                                "openid.realm=http://" + REALM + "&" +
                                "openid.return_to=http://" + REALM + "/steam_success"

                        BGBox(){
                            AndroidView(
                                factory = {
                                    WebView(it).apply {
                                        webViewClient = object : WebViewClient() {
                                            override fun onPageFinished(
                                                view: WebView,
                                                url: String
                                            ) {
                                                val Url: Uri = Uri.parse(url)
                                                if (Url.authority.equals("ratatoskr.ru")) {

                                                    val userAccountUrl =
                                                        Uri.parse(Url.getQueryParameter("openid.identity"))

                                                    val userId = userAccountUrl.lastPathSegment

                                                    onAuthorizeChange(userId.toString())


                                                }
                                            }
                                        }
                                        settings.javaScriptEnabled = true
                                        loadUrl(url)
                                        setBackgroundColor(0);
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Black,
                                                Color.Black
                                            )
                                        )
                                    )
                            )
                        }


                        Box(modifier = Modifier.fillMaxSize()) {

                        }
                    }

                }

            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun SteamHeader(
    navController: NavController,
    state: SteamState,
    player_tier: String,
) {
    var tierImage by remember { mutableStateOf("http://ratatoskr.ru/app/img/tier/0.png") }
    var tierDescription by remember { mutableStateOf("Tier undefined") }
    var steamTitle by remember { mutableStateOf("Sign in with Steam") }

    tierImage =
        "http://ratatoskr.ru/app/img/tier/" + player_tier[0] + ".png"

    tierDescription = player_tier + " tier"

    when (state) {
        is SteamState.LoggedIntoSteam -> {
            steamTitle = state.steam_user_name
        }
    }

    /*
    when (state) {
        is ProfileState.IndefinedState -> {
            if (state.player_tier != "undefined") {
                tierImage =
                    "http://ratatoskr.ru/app/img/tier/" + state.player_tier[0] + ".png"
                tierDescription = state.player_tier[0] + " tier"
            }
        }
        is ProfileState.LoggedIntoSteam -> {
            if (state.player_tier != "undefined") {
                tierImage =
                    "http://ratatoskr.ru/app/img/tier/" + state.player_tier[0] + ".png"
                tierDescription = state.player_tier[0] + " tier"
            }
            steamTitle = state.steam_user_name
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
                    steamTitle,
                    color = Color.White,
                    fontSize = 16.sp,
                    lineHeight = 20.sp
                )

            }
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.clickable {
            navController.navigate(
                Screens.Tier.route
            )
        }) {

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
