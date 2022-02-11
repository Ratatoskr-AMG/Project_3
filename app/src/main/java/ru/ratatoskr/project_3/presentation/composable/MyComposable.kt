package ru.ratatoskr.project_3.presentation.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.activity.Screens
import ru.ratatoskr.project_3.presentation.theme.Project_3Theme

class MyComposable {

    val CDN_ADDR = "https://cdn.dota2.com"

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun HeroesScreen(heroes: List<Hero>,navController: NavController) {

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
            contentPadding = PaddingValues(0.dp),
            content = {
                items(heroes.size) { index ->
                    Card(

                        backgroundColor = Color.Cyan,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        elevation = 0.dp,
                    ) {
                        Button({ navController.navigate(Screens.Hero.route+"/"+heroes[index]) }){
                            Text(text = heroes[index].name)
                        }
                        Image(

                            painter = rememberImagePainter(
                                data = CDN_ADDR + heroes[index].img,
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
    fun WaitScreen() {
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

    @Composable
    fun FixScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 26.dp, end = 26.dp)
                .verticalScroll(state = ScrollState(0), enabled = false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text("FixScreen");
        }
    }

    @Composable
    fun HeroScreen(id:String) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 26.dp, end = 26.dp)
                .verticalScroll(state = ScrollState(0), enabled = false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text("HeroScreen"+id);
        }
    }

}