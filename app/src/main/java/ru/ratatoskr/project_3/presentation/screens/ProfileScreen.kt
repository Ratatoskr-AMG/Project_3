package ru.ratatoskr.project_3.presentation.screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ru.ratatoskr.project_3.ReadMe
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.activity.Screens
import ru.ratatoskr.project_3.presentation.viewmodels.HeroState
import ru.ratatoskr.project_3.presentation.viewmodels.HeroViewModel
import ru.ratatoskr.project_3.presentation.viewmodels.ProfileState
import ru.ratatoskr.project_3.presentation.viewmodels.ProfileViewModel

@ExperimentalFoundationApi
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    context: Context,
) {

    val REALM = "ratatoskr.ru"
    val url = "https://steamcommunity.com/openid/login?" +
            "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
            "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
            "openid.mode=checkid_setup&" +
            "openid.ns=http://specs.openid.net/auth/2.0&" +
            "openid.realm=http://" + REALM + "&" +
            "openid.return_to=http://" + REALM + "/steam_success"

    val viewState = viewModel.profile_state.observeAsState()

    when (val state = viewState.value) {
        is ProfileState.LoggedIntoSteam -> {
            ProfileCard(
                state.steam_user_id.toString()
            )
        }
        is ProfileState.IndefinedState -> steamInterfaceView(viewModel,url, context)
        is ProfileState.LoadingState -> profileLoadingView()
        is ProfileState.ErrorProfileState -> profileErrorView()

    }



    LaunchedEffect(key1 = Unit, block = {



    })


}

@Composable
fun steamInterfaceView(viewModel:ProfileViewModel,url: String, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        AndroidView(
            factory = {
                WebView(context).apply {

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            val Url: Uri = Uri.parse(url)
                            if (Url.authority.equals("ratatoskr.ru")) {
                                val userAccountUrl =
                                    Uri.parse(Url.getQueryParameter("openid.identity"))
                                val userId = userAccountUrl.lastPathSegment
                                Toast.makeText(
                                    context,
                                    "Your userId is: " + userId,
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e("TOHA", "userId:" + userId)

                                ReadMe.q2()

                                viewModel.isSteamLoggedSwitch(userId!!.toInt())

                                /*
                                http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=D076D1B0AD4391F8156F8EED08C597CE&steamids=76561198165608798
                                 */

                            }
                        }

                    }
                    settings.javaScriptEnabled = true
                    loadUrl(url)

                }

            }, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}

@Composable
fun ProfileCard(user_id: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center), text = user_id,
            color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 16.sp
        )
    }
}

@Composable
fun profileLoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center), text = "Loading",
            color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 16.sp
        )
    }
}

@Composable
fun profileErrorView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center), text = "Error",
            color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 16.sp
        )
    }
}


