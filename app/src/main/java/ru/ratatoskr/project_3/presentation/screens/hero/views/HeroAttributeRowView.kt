package ru.ratatoskr.project_3.presentation.screens.hero.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.screens.Screens


@Composable
fun HeroAttributeRowView(
    hero: Hero,
    column: String,
    name: String,
    value: String,
    navController: NavController
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0x55202020))
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
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(Screens.Attr.route + "/" + column + "/" + hero.id)
            }
    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Text(
                fontSize = 12.sp,
                color = Color.White,
                text = name
            )
        }
        Box(
            modifier = Modifier
                .padding(end = 20.dp)
        ) {
            Text(
                fontSize = 12.sp,
                color = Color.White,
                text = value
            )
        }
    }
}