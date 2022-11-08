package net.doheco.presentation.screens.steam.views

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import net.doheco.domain.utils.rememberForeverLazyListState
import net.doheco.presentation.screens.steam.models.SteamState
import net.doheco.presentation.theme.BGBox

@ExperimentalFoundationApi
@Composable
fun SteamSignInView(
    navController: NavController,
    state: SteamState,
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
                //.background(Color(0x55202020))
                .background(Color(0x000000))
        ) {

            stickyHeader {
                SteamHeaderView(navController, state, player_tier)
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
                        val REALM = "doheco.net"
                        val url = "https://steamcommunity.com/openid/login?" +
                                "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
                                "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
                                "openid.mode=checkid_setup&" +
                                "openid.ns=http://specs.openid.net/auth/2.0&" +
                                "openid.realm=https://" + REALM + "&" +
                                "openid.return_to=https://" + REALM + "/app/steam_success"

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
                                                if (Url.authority.equals("doheco.net")) {

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