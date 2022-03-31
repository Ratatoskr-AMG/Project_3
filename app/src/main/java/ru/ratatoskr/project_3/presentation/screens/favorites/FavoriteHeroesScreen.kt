package ru.ratatoskr.project_3.presentation.screens

import android.content.res.Resources
import android.provider.Settings.Global.getString
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.shaded.auto.common.AnnotationValues.getString
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.states.HeroListState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel
import ru.ratatoskr.project_3.R

@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    viewModel: HeroesListViewModel,
    navController: NavController
) {
    val viewState = viewModel.heroListState.observeAsState()

    when (val state = viewState.value) {
        is HeroListState.LoadedHeroListState<*> -> FavoritesListView(
            state.heroes,navController
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
    navController: NavController,
    onHeroClick: (Hero) -> Unit
) {
    val heroes = data.mapNotNull { it as? Hero }
    var scrollState = rememberForeverLazyListState(key = "Favorites")

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        LazyColumn(
            state=scrollState,
            modifier = Modifier
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
                                    .border(1.dp, Color(0xFF0d111c), CircleShape)
                                    .clickable {
                                        navController.popBackStack()
                                    }
                            ) {
                                Image(

                                    modifier = Modifier
                                        .width(15.dp)
                                        .height(15.dp),
                                    painter = rememberImagePainter(
                                        R.drawable.ic_back
                                    ),
                                    contentDescription = "Is hero favorite?"
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .padding(start = 30.dp)
                                    .height(70.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box() {
                                    Text(
                                        "Favorites",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        lineHeight = 20.sp
                                    )
                                }

                            }

                        }
                    }
                }
            }
            heroes.forEach {

                item {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
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
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(start = 20.dp, end = 20.dp)

                            .clickable {
                                onHeroClick(it)
                            }
                    ) {
                        Image(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            painter = rememberImagePainter(it.icon),
                            contentDescription = it.name
                        )
                        Text(
                            fontSize = 12.sp,
                            color = Color.White,
                            text = it.localizedName
                        )
                    }

                }

            }
        }
    }

}
