package net.doheco.presentation.screens.home

import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.home.models.HomeState
import net.doheco.presentation.screens.home.views.HomeView
import net.doheco.presentation.theme.LoadingView
import net.doheco.presentation.theme.MessageView


@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
) {

    when (val state = viewModel.homeState.observeAsState().value) {
        is HomeState.LoadedHomeState<*> -> HomeView(
            viewModel,
            viewModel.imageLoader,
            state.heroes,
            state.favoriteHeroes,
            {
                navController.navigate(Screens.Hero.route + "/" + it.id)
            }, {
                viewModel.getAllHeroesByStrSortByName(it)
            }
        )
        is HomeState.NoHomeState -> MessageView(stringResource(id = R.string.heroes_not_found))
        is HomeState.LoadingHomeState -> LoadingView(stringResource(id = R.string.loading))
        is HomeState.ErrorHomeState -> MessageView(stringResource(id = R.string.error))
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesSortByName()
        //viewModel.registerFirebaseEvent()
    })
}