package ru.ratatoskr.project_3.presentation.screens.tiers

import android.content.SharedPreferences
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import ru.ratatoskr.project_3.presentation.screens.tiers.models.TiersEvent
import ru.ratatoskr.project_3.presentation.screens.tiers.models.TiersState
import ru.ratatoskr.project_3.presentation.screens.tiers.views.TiersListView

@ExperimentalFoundationApi
@Composable
fun TiersScreen(
    navController: NavController,
    viewModel: TiersViewModel,
    appSharedPreferences: SharedPreferences
) {

    val viewState = viewModel.tiersState.observeAsState()

    when (val viewState = viewState.value) {
        is TiersState.IndefinedState -> {
            TiersListView(navController, viewState, appSharedPreferences) {
                viewModel.obtainEvent(
                    TiersEvent.OnTierChange(it)
                )
            }
        }
        is TiersState.DefinedState -> {
            TiersListView(navController, viewState, appSharedPreferences) {
                viewModel.obtainEvent(
                    TiersEvent.OnTierChange(it)
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
    })
}