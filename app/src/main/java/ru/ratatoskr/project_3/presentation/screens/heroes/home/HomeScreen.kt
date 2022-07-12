package ru.ratatoskr.project_3.presentation.screens.heroes.home

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import dagger.hilt.android.internal.Contexts.getApplication
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.states.HeroesListState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel


@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: HeroesListViewModel,
    navController: NavController,
) {

    when (val state = viewModel.heroesListState.observeAsState().value) {
        is HeroesListState.LoadedHeroesListState<*> -> HeroesListView(
            state.heroes, {
                navController.navigate(Screens.Hero.route + "/" + it.id)
            }, {
                viewModel.getAllHeroesByStrSortByName(it)
            }
        )
        is HeroesListState.NoHeroesListState -> MessageView("Heroes not found")
        is HeroesListState.LoadingHeroesListState -> LoadingView("Heroes are loading...")
        is HeroesListState.ErrorHeroesListState -> MessageView("Heroes error!")
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesSortByName()
    })
}


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalFoundationApi
@Composable
fun HeroesListView(
    data: List<Any?>,
    onHeroClick: (Hero) -> Unit,
    onHeroSearch: (String) -> Unit
) {
    val heroes = data.mapNotNull { it as? Hero }
    var offsetPosition by remember { mutableStateOf(0f) }
    var searchState by remember { mutableStateOf(TextFieldValue("", selection = TextRange.Zero)) }
    val focusRequesterTop = remember { FocusRequester() }
    var scrollState = rememberForeverLazyListState(key = "Overview")
    val configuration = LocalConfiguration.current
    var listColumnsCount = 4
    var listBannerHeight: Dp = 240.dp
    var lazYColumnSpaceBoxHeight: Dp = 210.dp
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            listColumnsCount = 7
            listBannerHeight = 480.dp
            lazYColumnSpaceBoxHeight = 480.dp
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Box(
            modifier = Modifier
                .background(
                    Color.Black,
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
                        offsetPosition += delta
                        return Offset.Zero
                    }
                }
            }

            val density = LocalDensity.current

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
                    .background(brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent

                        )
                    ))

                    .fillMaxSize()
            ) {

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(lazYColumnSpaceBoxHeight)
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
                stickyHeader {

                    Box(
                        modifier = Modifier
                            .height(128.dp)
                            .padding(start = 20.dp, end = 20.dp)
                            .padding(bottom = 20.dp, top = 16.dp)
                            .background(Color.Transparent)
                    ) {


                        TextField(
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.Black,
                                lineHeight = 220.sp,
                                textDecoration = TextDecoration.None
                            ),

                            value = searchState.text,
                            onValueChange = {
                                searchState = TextFieldValue(it, TextRange(it.length))

                                onHeroSearch(it)
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .focusRequester(focusRequesterTop)
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        //keyboardController?.hide()
                                    } else {
                                    }
                                }
                                .clip(RoundedCornerShape(5.dp))
                                .fillMaxWidth()
                                .background(Color.White),
                            //keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            //keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)

                        )
                    }
                }


                var listRowsCount = heroes.size / listColumnsCount
                if (heroes.size % listColumnsCount > 0) {
                    listRowsCount += 1
                }

                for (row in 0..listRowsCount-1) {

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

    LaunchedEffect(Unit) {

    }
}

/*save lazy list state*/
private data class KeyParams(
    val params: String = "",
    val index: Int,
    val scrollOffset: Int
)

private val SaveMap = mutableMapOf<String, KeyParams>()

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