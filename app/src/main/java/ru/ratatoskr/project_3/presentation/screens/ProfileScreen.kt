package ru.ratatoskr.project_3.presentation.screens

import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.domain.helpers.events.ProfileEvent
import ru.ratatoskr.project_3.domain.helpers.states.ProfileState
import ru.ratatoskr.project_3.presentation.viewmodels.ProfileViewModel

@ExperimentalFoundationApi
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {
    val viewState = viewModel.profileState.observeAsState()
    when (val state = viewState.value) {
        is ProfileState.IndefinedState -> {

            Log.e("TOHA","state="+state)

            steamWebView {
                viewModel.obtainEvent(ProfileEvent.OnSteamLogin(it))
            }
        }
        is ProfileState.LoggedIntoSteam -> {
            profileCard(
                state
            )
        }
        is ProfileState.LoadingState -> profileLoadingView()
        is ProfileState.ErrorProfileState -> profileErrorView()
    }
    LaunchedEffect(key1 = Unit, block = {
    })
}

@Composable
fun steamWebView(onAuthorizeChange: (String) -> Unit) {

    val REALM = "ratatoskr.ru"
    val url = "https://steamcommunity.com/openid/login?" +
            "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
            "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
            "openid.mode=checkid_setup&" +
            "openid.ns=http://specs.openid.net/auth/2.0&" +
            "openid.realm=http://" + REALM + "&" +
            "openid.return_to=http://" + REALM + "/steam_success"
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    )
                )
            )
    ) {
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

                                Log.e("TOHA", "userId:" + userId)

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
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.DarkGray
                        )
                    )
                )
        )
    }
}

@Composable
fun profileCard(state: ProfileState.LoggedIntoSteam) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    )
                )
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)

        ) {
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterEnd)
                    .width(60.dp)
                    .height(60.dp),
                painter = rememberImagePainter(state.steam_user_avatar),
                contentDescription = "Steam user #" + state.steam_user_id + " avatar"
            )

            Text(
                modifier = Modifier.align(Alignment.Center), text = state.steam_user_name,
                color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 16.sp
            )
        }
    }


}

@Composable
fun profileLoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    )
                )
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center), text = "Loading",
            color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 14.sp
        )
    }
}

@Composable
fun profileErrorView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    )
                )
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center), text = "Error",
            color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp
        )
    }
}


