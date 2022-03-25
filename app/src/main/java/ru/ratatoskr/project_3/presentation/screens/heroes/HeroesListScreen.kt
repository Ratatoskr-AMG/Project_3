package ru.ratatoskr.project_3.presentation.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
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
fun HeroesListScreen(
    viewModel: HeroesListViewModel,
    navController: NavController
) {

    when (val state = viewModel.heroListState.observeAsState().value) {
        is HeroListState.LoadedHeroListState<*> -> HeroesListView(state.heroes) {
            navController.navigate(Screens.Hero.route + "/" + it.id)
        }
        is HeroListState.NoHeroListState -> MessageView("Heroes not found")
        is HeroListState.LoadingHeroListState -> LoadingView("Heroes are loading...")
        is HeroListState.ErrorHeroListState -> MessageView("Heroes error!")
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesByName()
    })
}

@ExperimentalFoundationApi
@Composable
fun HeroesListView(data: List<Any?>, onHeroClick: (Hero) -> Unit) {
    val heroes = data.mapNotNull { it as? Hero }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier.background(
                Color.Red,
                // rounded corner to match with the OutlinedTextField
                shape = RoundedCornerShape(4.dp)
            ).fillMaxSize()
        ) {

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
                cells = GridCells.Fixed(count = 4),
                content = {
                    item(span = { GridItemSpan(4) }){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            Image(
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                painter = rememberImagePainter("https://janssencosmetics.ru/app/img/HeroesList.jpg"),
                                contentDescription = "Welcome"
                            )
                        }
                    }

                    heroes.forEach {
                        item {
                            Box(modifier = Modifier
                                .clickable {
                                    onHeroClick(it)
                                }
                                .padding(10.dp)
                                .width(100.dp)
                                .height(45.dp)) {
                                Image(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(45.dp),
                                    painter = rememberImagePainter(it.icon),
                                    contentDescription = it.name
                                )
                            }
                        }
                    }
                })
        }
    }

}
