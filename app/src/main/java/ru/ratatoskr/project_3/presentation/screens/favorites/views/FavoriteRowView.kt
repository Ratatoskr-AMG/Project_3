package ru.ratatoskr.project_3.presentation.screens.favorites.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.domain.model.Hero

@Composable
fun FavoriteRowView(onHeroClick:(Hero)->Unit, it: Hero){

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .drawWithContent {
                drawContent()
                /*clipRect { // Not needed if you do not care about painting half stroke outside
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

                } )*/
            }
            .fillMaxWidth()
            .height(50.dp)

            .background(Color.Black)

    ) {
        Box(
            modifier = Modifier
                .padding(start=20.dp)
        ) {
        Image(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .clickable {
                    onHeroClick(it)
                },
            painter = rememberImagePainter(it.icon),
            contentDescription = it.name
        )
          }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end = 20.dp)
                .fillMaxWidth()){
            Text(
                modifier = Modifier.padding(15.dp,0.dp,20.dp,0.dp).clickable {
                    onHeroClick(it)
                },
                fontSize = 12.sp,
                color = Color.White,
                text = it.localizedName
            )
            /*
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint=Color.White
            )
            */
            Image(

                modifier = Modifier
                    .width(10.dp)
                    .height(10.dp),
                painter = rememberImagePainter(
                    R.drawable.ic_back
                ),
                contentDescription = "Drop from favorites"
            )
        }


    }
}