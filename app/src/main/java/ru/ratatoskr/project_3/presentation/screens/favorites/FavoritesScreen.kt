package ru.ratatoskr.project_3.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import ru.ratatoskr.project_3.presentation.screens.favorites.FavoritesViewModel
import ru.ratatoskr.project_3.presentation.screens.favorites.models.FavoritesState
import ru.ratatoskr.project_3.presentation.screens.favorites.views.EmptyFavoritesListView
import ru.ratatoskr.project_3.presentation.screens.favorites.views.FavoritesListView
import ru.ratatoskr.project_3.presentation.screens.favorites.views.LoadingFavoritesView

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
        is FavoritesState.NoHeroesState -> EmptyFavoritesListView(navController, "No heroes found")
        is FavoritesState.LoadingHeroesState -> LoadingFavoritesView(navController, "Favorites")
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllFavoriteHeroes()
    })
}