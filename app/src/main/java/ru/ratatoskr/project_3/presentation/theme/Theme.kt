package ru.ratatoskr.project_3.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun Project_3Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {

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
        content
    }

}

@Composable
fun MessageView(text:String) {

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
fun LoadingView(text:String) {
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
            modifier = Modifier.align(Alignment.Center).padding(bottom = 120.dp),
            textAlign = TextAlign.Center,
            text = text,
            color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp
        )
    }
}


