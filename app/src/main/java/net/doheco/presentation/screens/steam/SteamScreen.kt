package net.doheco.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.steam.models.SteamEvent
import net.doheco.presentation.screens.steam.SteamViewModel
import net.doheco.presentation.screens.steam.models.SteamState
import net.doheco.presentation.screens.steam.views.SteamSignInView

@ExperimentalFoundationApi
@Composable
fun SteamScreen(
    navController: NavController,
    viewModel: SteamViewModel,
) {

    val viewState = viewModel.steamState.observeAsState()

    var player_tier = viewModel.getPlayerTierFromSP()
    when (val state = viewState.value) {
        is SteamState.IndefinedState -> {
            SteamSignInView(navController, state, player_tier) {
                viewModel.obtainEvent(SteamEvent.OnSteamLogin(it))
            }
        }
        is SteamState.LoggedIntoSteam -> {
            navController.navigate(Screens.Profile.route)
            //SteamLoggedInView(navController, state, state.player_tier)
        }
        else -> {}
    }
    LaunchedEffect(key1 = Unit, block = {
    })
}