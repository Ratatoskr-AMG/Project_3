package ru.ratatoskr.project_3.presentation.screens.home

import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.presentation.screens.Screens
import ru.ratatoskr.project_3.presentation.screens.home.models.HomeState
import ru.ratatoskr.project_3.presentation.screens.home.views.HomeListView
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView


@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
) {

    when (val state = viewModel.homeState.observeAsState().value) {
        is HomeState.LoadedHomeState<*> -> HomeListView(
            state.heroes, {
                navController.navigate(Screens.Hero.route + "/" + it.id)
            }, {
                viewModel.getAllHeroesByStrSortByName(it)
            }
        )
        is HomeState.NoHomeState -> MessageView(stringResource(id = R.string.heroes_not_found))
        is HomeState.LoadingHomeState -> LoadingView(stringResource(id = R.string.loading))
        is HomeState.ErrorHomeState -> MessageView(stringResource(id = R.string.error))
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesSortByName()
    })
}