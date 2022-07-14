package ru.ratatoskr.project_3.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.states.HeroesListState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.screens.home.rememberForeverLazyListState
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel

@ExperimentalFoundationApi
@Composable
fun RoleScreen(
    role: String,
    viewModel: HeroesListViewModel,
    navController: NavController
) {
    val viewState = viewModel.heroesListState.observeAsState()

    when (val state = viewState.value) {

        is HeroesListState.LoadedHeroesListState<*> -> RoleListView(role, state.heroes, navController) {
            navController.navigate(Screens.Hero.route + "/" + it.id)
        }
        is HeroesListState.NoHeroesListState -> MessageView("Heroes not found")
        is HeroesListState.LoadingHeroesListState -> LoadingView("Heroes loading...")
        is HeroesListState.ErrorHeroesListState -> MessageView("Heroes error!")
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
    navController: NavController,
    onHeroClick: (Hero) -> Unit
) {
    val configuration = LocalConfiguration.current
    val heroes = data.mapNotNull { it as? Hero }
    var scrollState = rememberForeverLazyListState(key = "Role_" + role)
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = consumed.y
                return Offset.Zero
            }
        }
    }
    var listColumnsCount = 4
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            listColumnsCount = 7
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .nestedScroll(nestedScrollConnection)
                .fillMaxSize()
                .background(Color(0x55202020))
        ) {

            stickyHeader {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color.Black)
                ) {
                    Row(
                        modifier = Modifier
                            .drawWithContent {
                                drawContent()
                                clipRect { // Not needed if you do not care about painting half stroke outside
                                    val strokeWidth = Stroke.DefaultMiter
                                    val y = size.height // strokeWidth
                                    drawLine(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFF0d111c),
                                                Color(0xFF0d111c),
                                                Color(0xFF0d111c),
                                                //Color(0xFF000022),
                                                //Color(0xFF000022)
                                            )
                                        ),
                                        strokeWidth = strokeWidth,
                                        cap = StrokeCap.Square,
                                        start = Offset.Zero.copy(y = y),
                                        end = Offset(x = size.width, y = y)
                                    )
                                }
                            }
                            .fillMaxSize()
                            .padding(start = 20.dp, end = 20.dp, top = 45.dp, bottom = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Row() {


                            Box(contentAlignment = Alignment.Center,

                                modifier = Modifier

                                    .size(70.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color(0x880d111c), CircleShape)
                                    .clickable {
                                        navController.popBackStack()
                                    }
                            ) {
                                Image(

                                    modifier = Modifier
                                        .width(15.dp)
                                        .height(15.dp),
                                    painter = rememberImagePainter(
                                        ru.ratatoskr.project_3.R.drawable.ic_back
                                    ),
                                    contentDescription = "Back"
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .height(70.dp)
                                    .width(140.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Box() {
                                    Text(
                                        role,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        lineHeight = 20.sp
                                    )
                                }

                            }

                        }
                    }
                }
            }

            var listRowsCount = heroes.size / listColumnsCount
            if (heroes.size % listColumnsCount > 0) {
                listRowsCount += 1
            }

            for (row in 0..listRowsCount - 1) {

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (column in 0..listColumnsCount) {
                            var index = column + (row * listColumnsCount)
                            if (index <= heroes.size - 1) {
                                var hero = heroes.get(index)
                                Box(modifier = Modifier
                                    .clickable {
                                        onHeroClick(hero)
                                    }
                                    .width(70.dp)
                                    .padding(10.dp)
                                    .height(35.dp)) {
                                    Image(
                                        modifier = Modifier
                                            .width(70.dp)
                                            .height(35.dp),
                                        painter = rememberImagePainter(hero.icon),
                                        contentDescription = hero.name
                                    )
                                }

                            } else {
                                Box(
                                    modifier = Modifier
                                        .width(70.dp)
                                        .padding(10.dp)
                                        .height(35.dp)
                                ) {
                                }
                            }


                        }

                    }

                }
            }
        }
    }


}