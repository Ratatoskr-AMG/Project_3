package net.doheco.presentation.screens.tiers

import android.content.SharedPreferences
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import net.doheco.presentation.screens.tiers.models.TiersEvent
import net.doheco.presentation.screens.tiers.models.TiersState
import net.doheco.presentation.screens.tiers.views.TiersListView

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
                navController.popBackStack()
            }
        }
        is TiersState.DefinedState -> {
            TiersListView(navController, viewState, appSharedPreferences) {
                viewModel.obtainEvent(
                    TiersEvent.OnTierChange(it)
                )
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
    })
}