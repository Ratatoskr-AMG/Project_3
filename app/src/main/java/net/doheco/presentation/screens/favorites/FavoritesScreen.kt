package net.doheco.presentation.screens.favorites

import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.favorites.FavoritesViewModel
import net.doheco.presentation.screens.favorites.models.FavoritesState
import net.doheco.presentation.screens.favorites.views.EmptyFavoritesListView
import net.doheco.presentation.screens.favorites.views.FavoritesView
import net.doheco.presentation.screens.favorites.views.LoadingFavoritesView
import net.doheco.R

@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    navController: NavController
) {
    val viewState = viewModel.favoritesState.observeAsState()

    when (val state = viewState.value) {
        is FavoritesState.LoadedHeroesState<*> -> FavoritesView(
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