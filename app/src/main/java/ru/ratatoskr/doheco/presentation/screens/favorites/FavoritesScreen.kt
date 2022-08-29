package ru.ratatoskr.doheco.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.presentation.screens.Screens
import ru.ratatoskr.doheco.presentation.screens.favorites.FavoritesViewModel
import ru.ratatoskr.doheco.presentation.screens.favorites.models.FavoritesState
import ru.ratatoskr.doheco.presentation.screens.favorites.views.EmptyFavoritesListView
import ru.ratatoskr.doheco.presentation.screens.favorites.views.FavoritesListView
import ru.ratatoskr.doheco.presentation.screens.favorites.views.LoadingFavoritesView

@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    navController: NavController
) {
    val viewState = viewModel.favoritesState.observeAsState()

    when (val state = viewState.value) {
        is FavoritesState.LoadedHeroesState<*> -> FavoritesListView(
            viewModel, state.heroes, navController
        ) {
            navController.navigate(Screens.Hero.route + "/" + it.id)
        }
        is FavoritesState.NoHeroesState -> EmptyFavoritesListView(navController, stringResource(R.string.heroes_not_found))
        is FavoritesState.LoadingHeroesState -> LoadingFavoritesView(navController, stringResource(R.string.title_favorites))
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllFavoriteHeroes()
    })
}