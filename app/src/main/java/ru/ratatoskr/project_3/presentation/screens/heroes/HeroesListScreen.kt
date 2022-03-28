package ru.ratatoskr.project_3.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
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

            val listState = rememberLazyListState()
            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                        val delta = available.y
                        // called when you scroll the content
                        //Log.e("TOHA", "Delta:$delta");
                        return Offset.Zero
                    }
                }
            }
            val configuration = LocalConfiguration.current
            var listColumnsCount = 4
            var listBannerHeight: Dp = 250.dp
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    listColumnsCount=5
                    listBannerHeight=370.dp
                }
                else -> {

                }
            }

            LazyVerticalGrid(
                state = listState,
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black,
                                Color.Black
                                //Color(0xFF000022),
                                //Color(0xFF000022)
                            )
                        )
                    ),
                cells = GridCells.Fixed(count = listColumnsCount),
                content = {
                    item(span = { GridItemSpan(listColumnsCount) }){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(listBannerHeight)
                        ) {
                            Image(
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(listBannerHeight),
                                painter = rememberImagePainter("http://ratatoskr.ru/app/img/HeroesList.jpg"),
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
