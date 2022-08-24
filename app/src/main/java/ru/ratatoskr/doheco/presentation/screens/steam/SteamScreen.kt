package ru.ratatoskr.doheco.presentation.screens

import android.content.SharedPreferences
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import ru.ratatoskr.doheco.presentation.screens.steam.models.SteamEvent
import ru.ratatoskr.doheco.presentation.screens.steam.SteamViewModel
import ru.ratatoskr.doheco.presentation.screens.steam.models.SteamState
import ru.ratatoskr.doheco.presentation.screens.steam.views.SteamLoggedInView
import ru.ratatoskr.doheco.presentation.screens.steam.views.SteamSignInView

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