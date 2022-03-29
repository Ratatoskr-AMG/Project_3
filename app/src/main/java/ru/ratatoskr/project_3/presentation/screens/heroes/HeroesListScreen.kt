package ru.ratatoskr.project_3.presentation.screens

import android.R.attr.visible
import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.android.exoplayer2.util.Log
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
fun searchBox(curr:String,onValueChange:(String)->Unit){
    Box(
        modifier = Modifier
            .height(70.dp)
            .padding(start = 20.dp, end = 20.dp)
            .padding(bottom = 20.dp)
    ) {
        TextField(
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color= Color.Black,lineHeight = 220.sp, textDecoration = TextDecoration.None),
            value = curr,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}
private val SaveMap = mutableMapOf<String, KeyParams>()

private data class KeyParams(
    val params: String = "",
    val index: Int,
    val scrollOffset: Int
)

@Composable
fun rememberForeverLazyListState(
    key: String,
    params: String = "",
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0
): LazyListState {
    val scrollState = rememberSaveable(saver = LazyListState.Saver) {
        var savedValue = SaveMap[key]
        if (savedValue?.params != params) savedValue = null
        val savedIndex = savedValue?.index ?: initialFirstVisibleItemIndex
        val savedOffset = savedValue?.scrollOffset ?: initialFirstVisibleItemScrollOffset
        LazyListState(
            savedIndex,
            savedOffset
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            val lastIndex = scrollState.firstVisibleItemIndex
            val lastOffset = scrollState.firstVisibleItemScrollOffset
            SaveMap[key] = KeyParams(params, lastIndex, lastOffset)
        }
    }
    return scrollState
}

@ExperimentalFoundationApi
@Composable
fun HeroesListView(data: List<Any?>, onHeroClick: (Hero) -> Unit) {
    val heroes = data.mapNotNull { it as? Hero }
    var position by remember { mutableStateOf(0f) }
    var visible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier

            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .background(
                    Color.Red,
                    // rounded corner to match with the OutlinedTextField
                    shape = RoundedCornerShape(4.dp)
                )
                .fillMaxSize()
        ) {

            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPostScroll(
                        consumed: Offset,
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        val delta = consumed.y
                        position+=delta
                        Log.e("TOHA","delta:"+delta)
                        Log.e("TOHA","position:"+position)
                        visible = position<-1049
                        return Offset.Zero
                    }
                }
            }

            val configuration = LocalConfiguration.current
            var listColumnsCount = 4
            var listBannerHeight: Dp = 250.dp
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    listColumnsCount = 5
                    listBannerHeight = 370.dp
                }
                else -> {

                }
            }
            var text by remember { mutableStateOf("") }

            val density = LocalDensity.current
            val scrollState = rememberScrollState()
            LazyVerticalGrid(
                state = rememberForeverLazyListState(key = "Overview"),
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
                cells = GridCells.Fixed(count = listColumnsCount)
            ) {
                item(span = { GridItemSpan(listColumnsCount) }) {
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
                item(span = { GridItemSpan(listColumnsCount) }) {

                    searchBox(curr = text, onValueChange = {
                        text = it
                        //Log.e("TOHA", it)
                        Log.e("TOHA", "scrollState:"+scrollState.value)
                    })
/*
                    Box(
                        modifier = Modifier
                            .height(70.dp)
                            .padding(start = 20.dp, end = 20.dp)
                            .padding(bottom = 20.dp)
                    ) {
                        TextField(
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(color= Color.Black,lineHeight = 220.sp, textDecoration = TextDecoration.None),
                            value = text,
                            onValueChange = {
                                text = it
                                Log.e("TOHA", it)
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .fillMaxWidth()
                                .background(Color.White)
                        )
                    }
                    */
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
            }

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ){

                Box(modifier=Modifier.padding(top=30.dp)){
                    searchBox(curr = text, onValueChange = {
                        text = it
                        Log.e("TOHA", it)
                    })
                }

            }



        }
    }

}
