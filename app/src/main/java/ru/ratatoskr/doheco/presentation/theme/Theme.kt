package ru.ratatoskr.doheco.presentation.theme

import android.media.Image
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.R
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
                        painter = rememberImagePainter(
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
        Column{
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


