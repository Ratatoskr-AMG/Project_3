package ru.ratatoskr.project_3.presentation.screens

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import ru.ratatoskr.project_3.ReadMe
import ru.ratatoskr.project_3.presentation.viewmodels.ProfileState
import ru.ratatoskr.project_3.presentation.viewmodels.ProfileViewModel

@ExperimentalFoundationApi
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    context: Context,
) {

    val viewState = viewModel.profile_state.observeAsState()

    when (val state = viewState.value) {
        is ProfileState.LoggedIntoSteam -> {
            ProfileCard(
                state.steam_user_id.toString()
            )
        }
        is ProfileState.IndefinedState -> steamWebView(context,viewModel)
        is ProfileState.LoadingState -> profileLoadingView()
        is ProfileState.ErrorProfileState -> profileErrorView()

    }

    LaunchedEffect(key1 = Unit, block = {

    })


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

@Composable
fun steamWebView(context: Context,viewModel:ProfileViewModel) {

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
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        AndroidView(
            factory = {
                WebView(context).apply {
                    webViewClient = webViewClient(context,viewModel)
                    //settings.safeBrowsingEnabled =false
                    settings.javaScriptEnabled = true
                    loadUrl(url)

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}

class webViewClient(context:Context,viewModel: ProfileViewModel) : WebViewClient() {

    val context = context;
    val viewModel = viewModel;

    override fun onPageFinished(view: WebView, url: String) {

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
            viewModel.steamLogin(userId!!.toInt())

        }

    }

/*
sealed class ProfileEvent {
    data class SteamIdReceived(val steam_user_id: Int) : ProfileEvent()
}
*/
}

