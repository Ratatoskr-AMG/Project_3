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
import kotlinx.serialization.json.Json
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.activity.Screens
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListState
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel

@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    viewModel: HeroesListViewModel,
    navController: NavController
) {
    val viewState = viewModel.heroesListState.observeAsState()

    when (val state = viewState.value) {
        is HeroesListState.LoadedHeroesListState<*> -> FavoritesListView(
            state.heroes,
            navController
        ) {
            val json = Json {
                ignoreUnknownKeys = true
            }
        }
        is HeroesListState.NoHeroesListState -> NoHeroesView()
        is HeroesListState.LoadingHeroesListState -> LoadingHeroesView()
        is HeroesListState.ErrorHeroesListState -> NoHeroesView()
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllFavoriteHeroes()
    })
}


@ExperimentalFoundationApi
@Composable
fun FavoritesListView(
    data: List<Any?>,
    navController: NavController,
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
                        Color.DarkGray
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
                                onHeroClick.invoke(it)
                                navController.navigate(Screens.Hero.route + "/" + it.id)
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
