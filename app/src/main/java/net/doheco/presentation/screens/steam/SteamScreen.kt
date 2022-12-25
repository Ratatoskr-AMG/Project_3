package net.doheco.presentation.screens.steam

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.doheco.domain.utils.rememberForeverLazyListState
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.steam.models.SteamEvent
import net.doheco.presentation.screens.steam.models.SteamState
import net.doheco.presentation.screens.steam.views.SteamHeaderView
import net.doheco.presentation.screens.steam.views.SteamSignInView
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter.All

@ExperimentalFoundationApi
@Composable
fun SteamScreen(
    navController: NavController,
    viewModel: SteamViewModel,
) {
    val viewState = viewModel.steamState.observeAsState()
    val playerTier = viewModel.getPlayerTierFromSP()

    when (val state = viewState.value) {
        is SteamState.IndefinedState -> {
            SteamSignInView(navController, state, playerTier) {
                viewModel.obtainEvent(SteamEvent.OnSteamLogin(it))
            }
        }
        is SteamState.LoggedIntoSteam -> {
            navController.navigate(Screens.Profile.route)
        }
        is SteamState.ErrorSteamState -> {
            ErrorScreen(navController, state, playerTier)
        }
        else -> {}
    }
    LaunchedEffect(key1 = Unit, block = {
    })
}

/**
 * Вынести в отдельный экран ошибку
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ErrorScreen(
    navController: NavController,
    state: SteamState.ErrorSteamState,
    player_tier: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        SteamHeaderView(navController, state, player_tier)

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.error,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}