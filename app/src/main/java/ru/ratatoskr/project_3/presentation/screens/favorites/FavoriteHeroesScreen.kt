package ru.ratatoskr.project_3.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.states.HeroListState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel

@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    viewModel: HeroesListViewModel,
    navController: NavController
) {
    val viewState = viewModel.heroListState.observeAsState()

    when (val state = viewState.value) {
        is HeroListState.LoadedHeroListState<*> -> FavoritesListView(
            state.heroes
        ) {
            navController.navigate(Screens.Hero.route + "/" + it.id)
        }
        is HeroListState.NoHeroListState -> MessageView("Heroes not found")
        is HeroListState.LoadingHeroListState -> LoadingView("Favorites loading...")
        is HeroListState.ErrorHeroListState -> MessageView("Heroes error!")
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllFavoriteHeroes()
    })
}


@ExperimentalFoundationApi
@Composable
fun FavoritesListView(
    data: List<Any?>,
    onHeroClick: (Hero) -> Unit
) {
    val heroes = data.mapNotNull { it as? Hero }


    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Black
                    )
                )
            ),
        cells = GridCells.Fixed(count = 1),
        content = {
            heroes.forEach {

                item {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(1.dp)
                            .background(Color.Black)
                            .clickable {
                                onHeroClick(it)
                            }
                    ) {
                        Image(
                            modifier = Modifier
                                .width(50.dp)
                                .height(30.dp),
                            painter = rememberImagePainter(it.icon),
                            contentDescription = it.name
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            lineHeight = 50.sp,
                            color = Color.White,
                            text = it.localizedName
                        )
                    }

                }

            }
        })

}
