package ru.ratatoskr.project_3.presentation.screens.favorites.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.utils.rememberForeverLazyListState

@ExperimentalFoundationApi
@Composable
fun FavoritesListView(
    data: List<Any?>,
    navController: NavController,
    onHeroClick: (Hero) -> Unit
) {
    val heroes = data.mapNotNull { it as? Hero }
    var scrollState = rememberForeverLazyListState(key = "Favorites")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            state = scrollState,
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
                                        R.drawable.ic_back
                                    ),
                                    contentDescription = "Is hero favorite?"
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .height(70.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box() {
                                    Text(
                                        "Favorites",
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