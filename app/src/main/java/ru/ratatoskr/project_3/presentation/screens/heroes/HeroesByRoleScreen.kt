package ru.ratatoskr.project_3.presentation.screens

import android.util.Log
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
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.states.HeroListState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel

@ExperimentalFoundationApi
@Composable
fun HeroesByRoleScreen(
    role: String,
    viewModel: HeroesListViewModel,
    navController: NavController
) {
    val viewState = viewModel.heroListState.observeAsState()

    when (val state = viewState.value) {

        is HeroListState.LoadedHeroListState<*> -> RoleListView(role, state.heroes) {
            navController.navigate(Screens.Hero.route + "/" + it.id)
        }
        is HeroListState.NoHeroListState -> MessageView("Heroes not found")
        is HeroListState.LoadingHeroListState -> LoadingView("Heroes loading...")
        is HeroListState.ErrorHeroListState -> MessageView("Heroes error!")
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesByRole(role)
    })
}


@ExperimentalFoundationApi
@Composable
fun RoleListView(
    role: String,
    data: List<Any?>,
    onHeroClick: (Hero) -> Unit
) {

    val heroes = data.mapNotNull { it as? Hero }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.Black)
    ) {
        Text(
            modifier = Modifier
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .align(Alignment.CenterEnd),
            color = Color.White,
            text = role
        )
    }

    LazyVerticalGrid(
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxWidth()
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
                            onHeroClick(it)
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
