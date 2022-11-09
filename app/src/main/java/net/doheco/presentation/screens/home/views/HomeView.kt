package net.doheco.presentation.screens.home.views

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import net.doheco.R
import net.doheco.domain.model.Hero
import net.doheco.domain.utils.rememberForeverLazyListState
import net.doheco.presentation.screens.home.HomeViewModel
import net.doheco.presentation.theme.*


@ExperimentalFoundationApi
@Composable
fun HomeView(
    viewModel: HomeViewModel,
    imageLoader: ImageLoader,
    heroes: List<Any?>,
    favoriteHeroes: List<Any?>,
    onHeroClick: (Hero) -> Unit,
    onHeroSearch: (String) -> Unit,
    widthSizeClass: WindowWidthSizeClass
) {
    val heroes = heroes.mapNotNull { it as? Hero }

    for(hero in heroes){
        Log.e("TOHA_extra",hero.img)
        Log.e("TOHA_extra",hero.icon)
    }

    var offsetPosition by remember { mutableStateOf(0f) }
    var searchState by remember { mutableStateOf(TextFieldValue("", selection = TextRange.Zero)) }
    val focusRequesterTop = remember { FocusRequester() }
    val scrollState = rememberForeverLazyListState(key = "Home")
    var listColumnsCount = 4
    if (widthSizeClass == WindowWidthSizeClass.Expanded || widthSizeClass == WindowWidthSizeClass.Medium) {
        listColumnsCount = 9
    }
    var listRowsCount = heroes.size / (listColumnsCount + 1)
    if (heroes.size % (listColumnsCount + 1) > 0) {
        listRowsCount += 1
    }
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

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .nestedScroll(nestedScrollConnection)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent
                        )
                    )
                )
                .fillMaxSize()
        ) {
            item {
                appHeaderImage("https://doheco.net/app/img/HeroesList.jpg")
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
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            color = Color(0x00000000),
                            lineHeight = 220.sp,
                            textDecoration = TextDecoration.None
                        ),
                        placeholder = { Text(stringResource(R.string.search)) },
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
            for (row in 0 until listRowsCount) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (column in 0..listColumnsCount) {
                            var index = column + (row * (listColumnsCount + 1))
                            if (index <= heroes.size - 1) {
                                //var favoriteFlag = favoriteHeroes.isNotEmpty()
                                var hero = heroes.get(index)
                                var favoriteFlag = favoriteHeroes.contains(hero)
                                heroesListItemBox(onHeroClick,hero,favoriteFlag)
                            } else {
                                Box(
                                    modifier = Modifier
                                        .width(70.dp)
                                        .padding(10.dp)
                                        .height(35.dp)
                                )
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
