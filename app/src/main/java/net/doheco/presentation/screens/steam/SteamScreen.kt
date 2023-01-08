package net.doheco.presentation.screens.steam

import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import net.doheco.presentation.base.Screens
import net.doheco.presentation.base.screens.EmptyScreenBox
import net.doheco.presentation.screens.steam.models.SteamEvent
import net.doheco.presentation.screens.steam.models.SteamState
import net.doheco.presentation.screens.steam.views.ErrorScreen
import net.doheco.presentation.screens.steam.views.SteamSignInView

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
        else -> {
         EmptyScreenBox()
        }
    }
}