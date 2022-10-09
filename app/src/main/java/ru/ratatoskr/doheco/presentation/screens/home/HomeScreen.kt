package ru.ratatoskr.doheco.presentation.screens.home

import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.presentation.base.Screens
import ru.ratatoskr.doheco.presentation.screens.home.models.HomeState
import ru.ratatoskr.doheco.presentation.screens.home.views.HomeView
import ru.ratatoskr.doheco.presentation.theme.LoadingView
import ru.ratatoskr.doheco.presentation.theme.MessageView


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