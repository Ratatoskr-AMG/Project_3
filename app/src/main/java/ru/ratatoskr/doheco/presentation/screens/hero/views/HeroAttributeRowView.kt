package ru.ratatoskr.doheco.presentation.screens.hero.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.presentation.base.Screens
import ru.ratatoskr.doheco.presentation.theme.appHeader
import ru.ratatoskr.doheco.presentation.theme.loadPicture


@Composable
fun HeroAttributeRowView(
    hero: Hero,
    column: String,
    name: String,
    value: String,
    navController: NavController,
    max: Float,
    maxImg: String? = null
) {
    var rightText = value.toString()
    if (max != null) {
        rightText += "(" + max.toString() + ")"
    }


    val configuration = LocalConfiguration.current
    var blockHeight = 40.dp
    val screenWidth = configuration.screenWidthDp
    val valueLeft = name
    val attrValue = value.toFloat()

    val raz = max / screenWidth

    var leftBlockWidthFl = value.toFloat() / raz

    if (leftBlockWidthFl == 0f) {
        leftBlockWidthFl = 8f
    }


    var leftBlockWidth = leftBlockWidthFl.dp
    var leftBlockColor = Color(0xFF016D19)

    /*
    val painter = loadPicture(
        url = maxImg,
        placeholder = painterResource(id = R.drawable.ic_comparing_gr)
    )
    */


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screens.Attr.route + "/" + column + "/" + hero.id)
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(blockHeight)
                    .background(leftBlockColor)
                    .width(leftBlockWidth)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0x00000000),
                                    Color(0xFF000000)
                                )
                            )
                        )
                        .width(5.dp)
                        .height(38.dp)

                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(blockHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(name=="_Herald wins"){
                val tierPainter = loadPicture(
                    url = "http://ratatoskr.ru/app/img/tier/1.png",
                    placeholder = painterResource(id = R.drawable.ic_comparing_gr)
                )
                Row(){
                    Box(
                        modifier = Modifier
                            .padding(start=10.dp,end = 0.dp),
                    ) {
                        Image(
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(20.dp),
                            painter = tierPainter,
                            contentDescription = name + " Tierimage"
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = name,
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontSize = 12.sp
                    )
                }
            }else{
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = name,
                    color = Color.White,
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp
                )
            }

            Row {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = value,
                    color = Color.White,
                    textAlign = TextAlign.Right,
                    fontSize = 12.sp
                )
                /*
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp),
                ) {
                    Image(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(20.dp),
                        painter = painter,
                        contentDescription = "Max " + name + " hero"
                    )
                }*/

            }


        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {

    }


    /*
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            /*.drawWithContent {
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
            }*/
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
                text = rightText
            )
        }
    }

    */
}