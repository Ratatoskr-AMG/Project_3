package ru.ratatoskr.doheco.presentation.theme

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.ratatoskr.doheco.domain.model.Hero

private val DarkColorPalette = darkColors(
    primary = BlackFull,
    primaryVariant = BlackFull,
    secondary = BlackFull
)

private val LightColorPalette = lightColors(
    primary = BlackFull,
    primaryVariant = BlackFull,
    secondary = BlackFull

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val appHeaderBoxHeight: Dp = 210.dp
private val appHeaderBoxHeightInner: Dp = 140.dp
private val backGroundMainColor: Color = Color.Black

@Composable
fun leftBtnHeaderBox(navController: NavController, leftBtnPainter: Int, title: String) {
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
                        painter = rememberAsyncImagePainter(
                            leftBtnPainter
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
                            title,
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

@ExperimentalFoundationApi
@Composable
fun LoadingScreen(text: String, navController: NavController, leftBtnPainter: Int, title: String) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            leftBtnHeaderBox(navController, leftBtnPainter, title)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    //.background(Color(0x55202020))
                    .background(Color(0x000000))
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 120.dp),
                    textAlign = TextAlign.Center,
                    text = text,
                    color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp
                )
            }
        }

    }


}

@Composable
fun DoHeCOTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,

        )

}

//high order fun.
@Composable
fun BGBox(content: @Composable () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Black
                    )
                )
            )

    ) {
        //content.invoke()
        content()
    }

}

@Composable
fun MessageView(text: String) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x00000039),
                        Color(0x00000039)
                    )
                )
            )
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = text,
            color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 14.sp
        )
    }
}

@Composable
fun LoadingView(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Black
                    )
                )
            )
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 120.dp),
            textAlign = TextAlign.Center,
            text = text,
            color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp
        )
    }
}

/* NEW AGE */

//All
@Composable
fun appHeader(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backGroundMainColor)
            .height(appHeaderBoxHeight)
    ) {
        content()
    }
}

@Composable
fun appHeaderInner(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .background(backGroundMainColor)
            .fillMaxWidth()
            .height(appHeaderBoxHeightInner)
    ) {
        content()
    }
}

//Home
@Composable
fun appHeaderImage(imageAddr: String) {

    val painter = loadPicture(
        url = imageAddr,
        placeholder = painterResource(id = ru.ratatoskr.doheco.R.drawable.ic_comparing_gr)
    )

    appHeader {
        Image(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(appHeaderBoxHeight),
            painter = painter,
            contentDescription = stringResource(id = ru.ratatoskr.doheco.R.string.welcome)
        )
    }
}

//Hero, Attr, Tier, Steam
@Composable
fun appHeaderDouble() {
    appHeader {}
}

@Composable
fun appHeaderUnderlinedCenterVerticalRow(horizontalArrangement:Arrangement.Horizontal, content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .drawWithContent {
                drawContent()
                clipRect {
                    val strokeWidth = Stroke.DefaultMiter
                    val y = size.height
                    drawLine(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF0d111c),
                                Color(0xFF0d111c),
                                Color(0xFF0d111c),
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
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically) {
        content()
    }
}
@Composable
fun appHeaderImageBox(content: @Composable () -> Unit){
    Box(
        modifier = Modifier
            .width(70.dp)
            .height(70.dp)
    ){
        content()
    }
}

@Composable
fun appHeaderImage(onClick: () -> Unit,imageAddr:String, contentDescription:String, alignment:Alignment, color:Color){

    var cScale: ContentScale = ContentScale.Crop

    val painter = loadPicture(
        url = imageAddr,
        placeholder = painterResource(id = ru.ratatoskr.doheco.R.drawable.ic_comparing_gr)
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        alignment = alignment,
        contentScale = cScale,
        modifier = Modifier
            .width(70.dp)
            .height(70.dp)
            .clip(CircleShape)
            .border(1.dp, color, CircleShape)
            .clickable{
                onClick()
            }
    )
}

@Composable
fun appHeaderBaseText(text:String){
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        color = Color.White,
        textAlign = TextAlign.Center,
        lineHeight = 20.sp,
        fontSize = 16.sp
    )
}

@Composable
fun appHeaderLeftImgText(text:String){
    Text(
        modifier = Modifier.padding(start=20.dp),
        text = text,
        color = Color.White,
        textAlign = TextAlign.Center,
        lineHeight = 20.sp,
        fontSize = 16.sp
    )
}

//Role, Favorites, Profile
@Composable
fun appHeaderSingleLeft() {
    appHeader {}
}

//Home, Role
@Composable
fun heroesLazyList() {

}

@Composable
fun loadPicture(url: String, placeholder: Painter): Painter {

    var state by remember {
        mutableStateOf(placeholder)
    }

    val options: RequestOptions =
        RequestOptions().autoClone().diskCacheStrategy(DiskCacheStrategy.ALL)
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
