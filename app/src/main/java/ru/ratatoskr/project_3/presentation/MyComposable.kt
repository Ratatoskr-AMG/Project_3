package ru.ratatoskr.project_3.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.theme.Project_3Theme

class MyComposable {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Heroes(heroes: List<Hero>) {

        LazyVerticalGrid(

            modifier = Modifier
                .fillMaxSize()
                .padding(start = 0.dp, end = 0.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Blue,
                            Color.Cyan
                        )
                    )
                ),
            cells = GridCells.Adaptive(128.dp),

            contentPadding = PaddingValues(
                start = 0.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 0.dp
            ),
            content = {
                items(heroes.size) { index ->
                    Card(
                        backgroundColor = Color.Cyan,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        elevation = 0.dp,
                    ) {
                        Image(

                            painter = rememberImagePainter(
                                data = "https://cdn.dota2.com" + heroes[index].getImg(),
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Blue,
                                            Color.Cyan
                                        )
                                    )
                                )
                        )
                    }
                }
            }
        )
    }

    @Composable
    fun Wrapper(inner: @Composable() () -> Unit) {
        Project_3Theme {
            Surface(
                color = MaterialTheme.colors.background
            ) {

                inner()

            }
        }
    }

    @Composable
    fun w8() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 26.dp, end = 26.dp)
                .verticalScroll(state = ScrollState(0), enabled = false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text("w8");
        }
    }

}