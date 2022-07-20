package ru.ratatoskr.project_3.presentation.screens

import android.content.SharedPreferences
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import ru.ratatoskr.project_3.presentation.screens.profile.models.ProfileEvent
import ru.ratatoskr.project_3.presentation.screens.profile.models.ProfileState
import ru.ratatoskr.project_3.presentation.screens.profile.ProfileViewModel
import ru.ratatoskr.project_3.presentation.screens.profile.views.DefinedBySteamProfileView
import ru.ratatoskr.project_3.presentation.screens.profile.views.UndefinedProfileView
import java.util.*

@ExperimentalFoundationApi
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    appSharedPreferences: SharedPreferences
) {

    val player_tier = viewModel.getPlayerTierFromSP()
    val player_steam_name = viewModel.getPlayerSteamNameFromSP()
    val heroes_list_last_modified =
        Date(appSharedPreferences.getLong("heroes_list_last_modified", 0))
            .toString()

    if (player_steam_name != "undefined") {
        viewModel.setSteamIsDefinedProfileState()
    } else {
        viewModel.setUndefinedProfileState()
    }

    val viewState = viewModel.profileState.observeAsState()

    when (val state = viewState.value) {
        is ProfileState.UndefinedState -> {

            UndefinedProfileView(
                state,
                viewModel,
                navController,
                player_tier,
                heroes_list_last_modified
            )

        }
        is ProfileState.SteamNameIsDefinedState -> {
            DefinedBySteamProfileView(
                state,
                viewModel,
                player_tier,
                heroes_list_last_modified,
                navController,
            ) { viewModel.obtainEvent(ProfileEvent.OnSteamExit) }

        }
    }

    LaunchedEffect(key1 = Unit, block = {

    })
}