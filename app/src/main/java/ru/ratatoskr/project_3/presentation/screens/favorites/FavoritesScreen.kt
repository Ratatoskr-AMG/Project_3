package ru.ratatoskr.project_3.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.domain.utils.rememberForeverLazyListState
import ru.ratatoskr.project_3.presentation.screens.favorites.FavoritesViewModel
import ru.ratatoskr.project_3.presentation.screens.favorites.models.FavoritesState
import ru.ratatoskr.project_3.presentation.screens.favorites.views.EmptyFavoritesListView
import ru.ratatoskr.project_3.presentation.screens.favorites.views.FavoritesListView
import ru.ratatoskr.project_3.presentation.screens.favorites.views.LoadingFavoritesView
import ru.ratatoskr.project_3.presentation.theme.LoadingScreen
import ru.ratatoskr.project_3.presentation.theme.leftBtnHeaderBox

@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    navController: NavController
) {
    val viewState = viewModel.favoritesState.observeAsState()

    when (val state = viewState.value) {
        is FavoritesState.LoadedHeroesState<*> -> FavoritesListView(
            state.heroes, navController
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