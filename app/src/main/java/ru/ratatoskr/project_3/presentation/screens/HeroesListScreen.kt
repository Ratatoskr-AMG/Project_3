package ru.ratatoskr.project_3.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
fun HeroesListScreen(
    heroesListviewModel: HeroesListViewModel,
    navController: NavController
) {
    val viewState = heroesListviewModel.heroesListState.observeAsState()

    when (val state = viewState.value) {
        is HeroesListState.LoadedHeroesListState<*> -> HeroesListView(state.heroes, navController)
        is HeroesListState.NoHeroesListState -> NoHeroesView()
        is HeroesListState.LoadingHeroesListState -> LoadingHeroesView()
        is HeroesListState.ErrorHeroesListState -> NoHeroesView()
    }

    LaunchedEffect(key1 = Unit, block = {
        heroesListviewModel.getAllHeroesByName()

    })
}

@Composable
fun NoHeroesView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    )
                )
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center), text = "No heroes found",
            color = Color.White, fontWeight = FontWeight.Medium, fontSize = 16.sp
        )
    }
}

@Composable
fun LoadingHeroesView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    )
                )
            )
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun HeroesListView(data: List<Any?>, navController: NavController) {
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
        cells = GridCells.Fixed(count = 4),
        content = {
            heroes.forEach {
                item {
                    Box(modifier = Modifier
                        .clickable {
                            navController.navigate(Screens.Hero.route + "/" + it.id)
                        }
                        .padding(10.dp)
                        .width(100.dp)
                        .height(60.dp)) {
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(60.dp),
                            painter = rememberImagePainter(it.icon),
                            contentDescription = it.name
                        )
                    }
                }
            }
        })

}
