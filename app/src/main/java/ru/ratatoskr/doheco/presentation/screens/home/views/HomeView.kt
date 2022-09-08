package ru.ratatoskr.doheco.presentation.screens.home.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.disk.DiskCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.utils.rememberForeverLazyListState
import ru.ratatoskr.doheco.presentation.screens.home.HomeViewModel
import ru.ratatoskr.doheco.presentation.theme.*


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalFoundationApi
@Composable
fun HomeView(
    viewModel: HomeViewModel,
    imageLoader: ImageLoader,
    data: List<Any?>,
    onHeroClick: (Hero) -> Unit,
    onHeroSearch: (String) -> Unit
) {
    val heroes = data.mapNotNull { it as? Hero }

    /*
    val heroes_base = data.mapNotNull { it as? Hero }
    var asd = heroes_base.toMutableList()
    var asdad = asd.sortedBy{it.proWin.toFloat()/it.proPick.toFloat()}
    var heroes = asdad.reversed().slice(0..9)

    Log.e("TOHAcomp","heroes_base:"+heroes_base.toString())
    Log.e("TOHAcomp","asdad:"+asdad.toString())
    Log.e("TOHAcomp","heroes:"+heroes.toString())
    */

    var offsetPosition by remember { mutableStateOf(0f) }
    var searchState by remember { mutableStateOf(TextFieldValue("", selection = TextRange.Zero)) }
    val focusRequesterTop = remember { FocusRequester() }
    var scrollState = rememberForeverLazyListState(key = "Home")
    var listColumnsCount = 4
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
                appHeaderImage("http://ratatoskr.ru/app/img/HeroesList.jpg")
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
            for (row in 0 until listRowsCount) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (column in 0..listColumnsCount) {
                            var index = column + (row * (listColumnsCount + 1))
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
                                        contentDescription = hero.name,
                                        painter = loadPicture(
                                            url = hero.icon,
                                            placeholder = painterResource(id = R.drawable.ic_comparing_gr)
                                        ),
                                        modifier = Modifier
                                            .width(70.dp)
                                            .height(35.dp)
                                    )
                                }
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