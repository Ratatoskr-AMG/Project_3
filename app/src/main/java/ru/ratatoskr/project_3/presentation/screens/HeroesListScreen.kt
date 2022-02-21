package ru.ratatoskr.project_3.presentation.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.ratatoskr.project_3.domain.helpers.State
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel


@ExperimentalFoundationApi
@Composable
fun HeroesListScreen(
    viewModel: HeroesListViewModel,
    navController: NavController
) {
    val viewState = viewModel.state.observeAsState()

    when (val state = viewState.value) {
        is State.LoadedState<*> -> HeroesListView(state.data) {
            val json = Json {
                ignoreUnknownKeys = true
            }
        }
        is State.NoItemsState -> NoHeroesView()
        is State.LoadingState -> LoadingHeroesView()
        is State.ErrorState -> NoHeroesView()
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchCarries()
    })
}

@Composable
fun NoHeroesView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center), text = "No heroes found",
            color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 16.sp
        )
    }
}

@Composable
fun LoadingHeroesView() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun HeroesListView(data: List<Any?>, onHeroClick: (Hero) -> Unit) {
    val heroes = data.mapNotNull { it as? Hero }
    Log.e("TOHA", "HeroesListView $heroes");
/*
    LazyColumn(modifier = Modifier.fillMaxSize(), content = {
        heroes.forEach {
            item {
                Box(modifier = Modifier
                    .clickable {
                        onHeroClick.invoke(it)
                    }
                    .width(100.dp)
                    .height(60.dp)) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberImagePainter("https://cdn.dota2.com"+it.icon),
                        contentDescription = it.img
                    )
                    Text(it.name)

                }
            }
        }
    }
    )

 */

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        cells = GridCells.Fixed(count = 4),
        content = {
            heroes.forEach {
                item {
                    Box(modifier = Modifier
                        .clickable {
                            onHeroClick.invoke(it)
                        }
                        .width(100.dp)
                        .height(60.dp)) {
                        Image(
                            painter = rememberImagePainter(it.img),
                            contentDescription = it.name
                        )
                    }
                }
            }
        })


}