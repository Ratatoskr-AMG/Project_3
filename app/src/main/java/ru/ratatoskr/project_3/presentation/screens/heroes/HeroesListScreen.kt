package ru.ratatoskr.project_3.presentation.screens

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.android.exoplayer2.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        is HeroListState.LoadedHeroListState<*> -> HeroesListView(
            state.heroes, {
                navController.navigate(Screens.Hero.route + "/" + it.id)
            }, {
                viewModel.getAllHeroesByStrSortByName(it)
            }
        )
        is HeroListState.NoHeroListState -> MessageView("Heroes not found")
        is HeroListState.LoadingHeroListState -> LoadingView("Heroes are loading...")
        is HeroListState.ErrorHeroListState -> MessageView("Heroes error!")
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
    val focusRequesterPopUp = remember { FocusRequester() }
    var keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    var scrollState = rememberForeverLazyListState(key = "Overview")
    var visible by remember { mutableStateOf(scrollState.firstVisibleItemScrollOffset < -1049) }
    val configuration = LocalConfiguration.current
    var listColumnsCount = 4
    var listBannerHeight: Dp = 240.dp
    val listState = rememberLazyListState()



    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {

        Box(
            modifier = Modifier
                .background(
                    Color.Black,
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
                        offsetPosition += delta
                        visible = offsetPosition < -1049
                        Log.e("TOHA", "offsetPosition:" + offsetPosition)
                        return Offset.Zero
                    }
                }
            }

            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    listColumnsCount = 5
                    listBannerHeight = 320.dp
                }
            }

            val density = LocalDensity.current

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .background(brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent
                            //Color(0xFF000022),
                            //Color(0xFF000022)
                        )
                    ))

                    .fillMaxSize()
            ) {

                item {
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
                                Log.e("TOHA", "Top onValueChange")
                                Log.e("TOHA", "Top value:" + searchState.text)
                                Log.e("TOHA", "Top selection1:" + searchState.selection.toString())
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
                                        Log.e("TOHA", "Top is focused")
                                    } else {
                                        Log.e("TOHA", "Top is not focused")
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
                Log.e("TOHA_CALC", "heroes.size:" + heroes.size)
                Log.e("TOHA_CALC", "listColumnsCount:" + listColumnsCount)
                Log.e("TOHA_CALC", "heroes.size/listColumnsCount:" + heroes.size / listColumnsCount)
                Log.e("TOHA_CALC", "heroes.size%listColumnsCount:" + heroes.size % listColumnsCount)

                var listRowsCount = heroes.size / listColumnsCount
                if (heroes.size % listColumnsCount > 0) {
                    listRowsCount += 1
                }

                for (row in 0..listRowsCount) {

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
                                        Log.e("TOHA_CALC", hero.name)
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
                /*
                item {
                    heroes.forEach {
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
            */

            }

/*
            LazyVerticalGrid(
                state = scrollState,
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


                    Box(
                        modifier = Modifier
                            .height(70.dp)
                            .padding(start = 20.dp, end = 20.dp)
                            .padding(bottom = 20.dp)
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
                                Log.e("TOHA", "Top onValueChange")
                                Log.e("TOHA", "Top value:" + searchState.text)
                                Log.e("TOHA", "Top selection1:" + searchState.selection.toString())
                                onHeroSearch(it)
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .focusRequester(focusRequesterTop)
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        keyboardController?.hide()
                                        Log.e("TOHA", "Top is focused")
                                    } else {
                                        Log.e("TOHA", "Top is not focused")
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
*/
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
            ) {
                Box(modifier = Modifier.padding(top = 30.dp)) {

                    Box(
                        modifier = Modifier
                            .height(70.dp)
                            .padding(start = 20.dp, end = 20.dp)
                            .padding(bottom = 20.dp)
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
                                scope.launch {
                                    scrollState.scrollToItem(0)
                                    delay(250)
                                    focusRequesterTop.requestFocus()
                                }
                                //onHeroSearch(it)
                                visible = false
                                offsetPosition = 0f

                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .focusRequester(focusRequesterPopUp)
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        Log.e("TOHA", "Popup is focused")
                                    } else {
                                        Log.e("TOHA", "Popup is not focused")
                                    }
                                }
                                .clip(RoundedCornerShape(5.dp))
                                .fillMaxWidth()
                                .background(Color.White),

                            )
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