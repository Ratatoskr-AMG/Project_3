package ru.ratatoskr.doheco.presentation.screens.home.views

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun loadPicture(url: String, placeholder: Painter? = null): Painter? {

    var state by remember {
        mutableStateOf(placeholder)
    }

    val options: RequestOptions = RequestOptions().autoClone().diskCacheStrategy(DiskCacheStrategy.ALL)
    val context = LocalContext.current
    val result = object : CustomTarget<Bitmap>() {
        override fun onLoadCleared(p: Drawable?) {
            state = placeholder
        }

        override fun onResourceReady(
            resource: Bitmap,
            transition: Transition<in Bitmap>?,
        ) {
            state = BitmapPainter(resource.asImageBitmap())
        }
    }
    try {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .apply(options)
            .into(result)
    } catch (e: Exception) {
        // Can't use LocalContext in Compose Preview
    }
    return state
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalFoundationApi
@Composable
fun HomeListView(
    viewModel: HomeViewModel,
    imageLoader: ImageLoader,
    data: List<Any?>,
    onHeroClick: (Hero) -> Unit,
    onHeroSearch: (String) -> Unit
) {


    val heroes = data.mapNotNull { it as? Hero }
    var offsetPosition by remember { mutableStateOf(0f) }
    var searchState by remember { mutableStateOf(TextFieldValue("", selection = TextRange.Zero)) }
    val focusRequesterTop = remember { FocusRequester() }
    var scrollState = rememberForeverLazyListState(key = "Home")
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


                var listRowsCount = heroes.size / (listColumnsCount + 1)
                if (heroes.size % (listColumnsCount + 1) > 0) {
                    listRowsCount += 1
                }

                for (row in 0..listRowsCount - 1) {

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
                                        /*Row(
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxSize()
                                            //.padding(top = 7.dp, start = 7.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(R.drawable.ic_comparing_gr),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .width(15.dp)
                                                    .height(15.dp)
                                            )
                                        }*/

                                        val localCurrentContext = LocalContext.current


                                        /*AsyncImage(
                                           model = ImageRequest.Builder(LocalContext.current)
                                               .data(hero.icon)
                                               .crossfade(true)
                                               .memoryCachePolicy(CachePolicy.ENABLED)
                                               .build(),
                                           contentDescription = hero.name,
                                           modifier = Modifier
                                               .width(70.dp)
                                               .height(35.dp),

                                       )

*/



                                        //viewModel.saveHeroImage(request)
                                        val request = ImageRequest.Builder(localCurrentContext)
                                            .data(hero.icon)
                                            //.diskCacheKey(hero.icon)
                                            /*.target({AsyncImage(
                                                model = hero.icon,
                                                contentDescription = hero.name,
                                                modifier = Modifier
                                                    .width(70.dp)
                                                    .height(35.dp)
                                        )})*/
                                            .size(Size.ORIGINAL)
                                            .build()

                                        imageLoader.enqueue(request)

                                        val painter = loadPicture(
                                            url = hero.icon,
                                            placeholder = painterResource(id = R.drawable.ic_comparing_gr)
                                        )

                                        //viewModel.saveHeroImage(request)
/*
                                        AsyncImage(
                                            model = hero.icon,
                                            modifier = Modifier
                                                .width(70.dp)
                                                .height(35.dp),
                                            contentDescription = hero.name,

                                        )*/


                                        Image(
                                            contentDescription = hero.name,
                                            painter = painter!!,
                                            modifier = Modifier
                                                .width(70.dp)
                                                .height(35.dp)
                                        )
                                        /*Image(
                                            rememberAsyncImagePainter(
                                                remember(hero.icon) {
                                                    ImageRequest.Builder(localCurrentContext)
                                                        .data(hero.icon)
                                                        .diskCacheKey(hero.icon)
                                                        .memoryCacheKey(hero.icon)
                                                        .diskCachePolicy(CachePolicy.ENABLED)
                                                        .build()
                                                }
                                            ),
                                            hero.name
                                        )*/


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