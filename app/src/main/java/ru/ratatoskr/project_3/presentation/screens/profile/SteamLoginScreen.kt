package ru.ratatoskr.project_3.presentation.screens

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.Questions
import ru.ratatoskr.project_3.domain.helpers.events.ProfileEvent
import ru.ratatoskr.project_3.domain.helpers.states.ProfileState
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.ProfileViewModel

@ExperimentalFoundationApi
@Composable
fun SteamLoginScreen(
    navController: NavController,
    viewState: State<ProfileState?>,
    viewModel: ProfileViewModel,
    appSharedPreferences: SharedPreferences,
) {

    when (val state = viewState.value) {
        is ProfileState.IndefinedState -> {
            SteamSignInView(navController, state,appSharedPreferences) {
                viewModel.obtainEvent(ProfileEvent.OnSteamLogin(it))
            }
        }
        is ProfileState.LoggedIntoSteam -> {
            SteamLoggedInView(navController, state)
        }
        is ProfileState.LoadingState -> {
            LoadingView("Profile is loading")
        }
        is ProfileState.ErrorProfileState -> {
            MessageView("Profile error!")
        }
    }
    LaunchedEffect(key1 = Unit, block = {
    })
}

@ExperimentalFoundationApi
@Composable
fun SteamLoggedInView(
    navController: NavController,
    state: ProfileState.LoggedIntoSteam,
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
                SteamSignedInHeader(navController, state)
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
                            text = "Welcome, " + state.steam_user_name + "!"
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
    state: ProfileState.IndefinedState,
    appSharedPreferences: SharedPreferences,
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
                SteamSignInHeader(navController,appSharedPreferences)
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
                        SteamWebView(
                            onAuthorizeChange
                        )
                    }

                }

            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun SteamSignedInHeader(
    navController: NavController,
    state: ProfileState.LoggedIntoSteam,
) {

    var tierImage = "http://ratatoskr.ru/app/img/tier/undefined.png"
    var tierDescription = "Tier undefined"
    if(state.player_tier!="undefined"){
        tierImage = "http://ratatoskr.ru/app/img/tier/" + state.player_tier[0] + ".png"
        tierDescription = state.player_tier[0]+" tier"
    }

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

            Row(horizontalArrangement = Arrangement.SpaceBetween) {

                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                        .border(1.dp, Color(0xFF0d111c), CircleShape)
                        .clip(CircleShape),
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
                        .padding(start = 20.dp)
                        .height(70.dp)
                        .width(140.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(top = 0.dp).width(210.dp),
                    ) {
                        Box() {
                            Text(
                                "Signed in with Steam",
                                color = Color.White,
                                fontSize = 16.sp,
                                lineHeight = 20.sp
                            )
                        }


                    }
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.Transparent)
                    .border(1.dp, Color(0x880d111c), CircleShape)
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

@ExperimentalFoundationApi
@Composable
fun SteamSignInHeader(
    navController: NavController,
    appSharedPreferences: SharedPreferences,
) {

    var tierImage = "http://ratatoskr.ru/app/img/tier/undefined.png"
    var tierDescription="Tier undefined"
    var spTier = appSharedPreferences.getString("player_tier", "undefined").toString()
    if(spTier!="undefined"){
        tierImage = "http://ratatoskr.ru/app/img/tier/" + spTier[0] + ".png"
        tierDescription = "Tier "+spTier[0]
        Log.e("TOHA","IndefinedState.spTier:"+spTier[0])
    }
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
                        .padding(start = 20.dp)
                        .height(70.dp)
                        .width(140.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(top = 0.dp).width(140.dp),
                    ) {
                        Box() {
                            Text(
                                "Sign in with Steam",
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

@Composable
fun SteamWebView(onAuthorizeChange: (String) -> Unit) {

    val REALM = "ratatoskr.ru"
    val url = "https://steamcommunity.com/openid/login?" +
            "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
            "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
            "openid.mode=checkid_setup&" +
            "openid.ns=http://specs.openid.net/auth/2.0&" +
            "openid.realm=http://" + REALM + "&" +
            "openid.return_to=http://" + REALM + "/steam_success"
    Questions.q3()
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                WebView(it).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView, url: String) {
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

}
