package ru.ratatoskr.doheco.presentation.screens.attribute.views

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.data.converters.pixelsToDp
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.utils.appUtilsArrays
import ru.ratatoskr.doheco.domain.utils.rememberForeverLazyListState
import ru.ratatoskr.doheco.presentation.screens.recommendations.views.recommendationsHeroesBlock
import ru.ratatoskr.doheco.presentation.screens.recommendations.views.recommendationsTitleBlock

@ExperimentalFoundationApi
@Composable
fun HeroesByAttributeListView(
    attr: String,
    id: String,
    data: List<Any?>,
    isSortAsc: Boolean,
    navController: NavController,
    onHeroClick: (Hero) -> Unit,
    onSortChange: (Boolean) -> Unit
) {
    val configuration = LocalConfiguration.current
    var context = LocalContext.current
    val heroes = data.mapNotNull { it as? Hero }

    var scrollState = rememberForeverLazyListState(key = "Attr_" + attr)
    var scrollIconPosition by remember { mutableStateOf(0F) }


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = consumed.y
                return Offset.Zero
            }
        }
    }
    var hero: Hero = heroes[0]
    heroes.forEach {
        if (id.toInt() == it.id) {
            hero = it
            return@forEach
        }
    }

    var titleValue = ""
    var heroIconPosition by remember { mutableStateOf(5F) }
    val attrsLanguageMap: Map<String, Int> = appUtilsArrays.attrsLanguageMap()

    titleValue = stringResource(attrsLanguageMap[attr]!!)

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Box(
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
                .fillMaxWidth()
                .height(140.dp)
                .background(Color.Black)
        ) {


            Row(
                modifier = Modifier
                    .background(Color.Black)
                    .height(140.dp)
                    .fillMaxWidth()
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
                                .width(13.dp)
                                .height(13.dp),
                            painter = rememberAsyncImagePainter(
                                R.drawable.ic_back
                            ),
                            contentDescription = "Back"
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .height(70.dp)
                            .width(140.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Box() {
                            Text(
                                titleValue,
                                color = Color.White,
                                fontSize = 16.sp,
                                lineHeight = 20.sp
                            )
                        }

                    }
                }


                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color.Transparent)
                        .clickable {
                            onSortChange(!isSortAsc)
                        }
                        .clip(CircleShape)
                        .border(1.dp, Color(0x880d111c), CircleShape)
                ) {
                    Image(
                        modifier = Modifier
                            .width(13.dp)
                            .height(20.dp),
                        painter = if (isSortAsc) rememberImagePainter(R.drawable.ic_down)
                        else rememberImagePainter(R.drawable.ic_up),
                        contentDescription = "Sort"
                    )
                }
            }


        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
                    .fillMaxSize()
            ) {

                stickyHeader {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF131313))
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 7.dp)
                        ) {


                            Box(modifier = Modifier
                                .height(13.dp)
                                .fillMaxWidth()
                                .drawWithContent {
                                    drawContent()
                                    clipRect {
                                        val strokeWidth = Stroke.DefaultMiter
                                        val y = size.height
                                        drawLine(
                                            brush = SolidColor(Color.Black),
                                            strokeWidth = strokeWidth*2,
                                            cap = StrokeCap.Square,
                                            start = Offset.Zero.copy(y = y),
                                            end = Offset(x = size.width, y = y)
                                        )
                                    }
                                }
                            )

                            Box(
                                modifier = Modifier
                                    .height(20.dp)
                                    .fillMaxWidth()
                                    .padding(top = 11.dp, start = scrollIconPosition.dp)
                                    .onGloballyPositioned {
                                        var size = it.parentLayoutCoordinates?.size?.toSize()
                                        var dpSize =
                                            pixelsToDp.convertPixelsToDp(size!!.width, context)
                                        var pointWidth = dpSize / heroes.size
                                        Log.e("TOHA3", "dpSize1:" + dpSize)
                                        Log.e("TOHA3", "pointWidth1:" + pointWidth)
                                        var padding = dpSize / 33
                                        //if (scrollState.firstVisibleItemIndex > heroes.size / 1.111 ) padding = 1
                                        scrollIconPosition =
                                            scrollState.firstVisibleItemIndex * pointWidth
                                    }
                            ) {


                                Box(
                                    modifier = Modifier
                                        .width(25.dp)
                                        .height(2.dp)
                                        .background(Color(0xFFc98000))
                                )


                            }

                            Box(
                                modifier = Modifier
                                    .height(25.dp)
                                    .fillMaxWidth()
                                    .padding(top = 0.dp, start = heroIconPosition.dp)
                                    .onGloballyPositioned {
                                        var size = it.parentLayoutCoordinates?.size?.toSize()
                                        var dpSize =
                                            pixelsToDp.convertPixelsToDp(size!!.width, context)
                                        var pointWidth = dpSize / heroes.size
                                        Log.e("TOHA3", "dpSize2:" + dpSize)
                                        Log.e("TOHA3", "pointWidth2:" + pointWidth)

                                        var padding = 7
                                        if (heroes.indexOf(hero) > heroes.size / 1.111) padding = 30

                                        heroIconPosition =
                                            (heroes.indexOf(hero) * pointWidth).minus(padding)

                                        if (heroIconPosition < 0) heroIconPosition = 5F

                                        Log.e("TOHA3", "heroIconPosition:" + heroIconPosition)
                                    }
                            ) {

                                Image(
                                    rememberImagePainter(hero.icon),
                                    modifier = Modifier
                                        .width(25.dp)
                                        .height(25.dp),
                                    contentDescription = stringResource(id = R.string.scroll_state),
                                )

                            }
                        }

                    }

                }

                heroes.forEach {

                    var attrValue = ""
                    var rowBackgroundColor = Color.Transparent
                    var rowTextColor = Color.White
                    if (id.toInt() == it.id) {
                        rowBackgroundColor = Color(0xFFc98000)
                        rowTextColor = Color.Black
                    }

                    when (attr) {
                        "legs" -> attrValue = it.legs.toString()
                        "baseHealth" -> attrValue = it.baseHealth.toString()
                        "baseHealthRegen" -> attrValue = "+" + it.baseHealthRegen.toString()
                        "baseMana" -> attrValue = it.baseMana.toString()
                        "baseManaRegen" -> attrValue = "+" + it.baseManaRegen.toString()
                        "baseArmor" -> attrValue = it.baseArmor.toString()
                        "baseMr" -> attrValue = it.baseMr.toString() + "%"
                        "baseStr" -> attrValue = it.baseStr.toString()
                        "baseAgi" -> attrValue = it.baseAgi.toString()
                        "baseInt" -> attrValue = it.baseInt.toString()
                        "strGain" -> attrValue = it.strGain.toString()
                        "agiGain" -> attrValue = it.agiGain.toString()
                        "intGain" -> attrValue = it.intGain.toString()
                        "attackRange" -> attrValue = it.attackRange.toString()
                        "projectileSpeed" -> attrValue = it.projectileSpeed.toString()
                        "attackRate" -> attrValue = it.attackRate.toString()
                        "moveSpeed" -> attrValue = it.moveSpeed.toString()
                        "cmEnabled" -> attrValue = it.cmEnabled
                        "turboPicks" -> attrValue = it.turboPicks.toString()
                        "turboWins" -> attrValue = it.turboWins.toString()
                        "proBan" -> attrValue = it.proBan.toString()
                        "proWin" -> attrValue = it.proWin.toString()
                        "proPick" -> attrValue = it.proPick.toString()
                        "_1Pick" -> attrValue = it._1Pick.toString()
                        "_1Win" -> attrValue = it._1Win.toString()
                        "_2Pick" -> attrValue = it._2Pick.toString()
                        "_2Win" -> attrValue = it._2Win.toString()
                        "_3Pick" -> attrValue = it._3Pick.toString()
                        "_3Win" -> attrValue = it._3Win.toString()
                        "_4Pick" -> attrValue = it._4Pick.toString()
                        "_4Win" -> attrValue = it._4Win.toString()
                        "_5Pick" -> attrValue = it._5Pick.toString()
                        "_5Win" -> attrValue = it._5Win.toString()
                        "_6Pick" -> attrValue = it._6Pick.toString()
                        "_6Win" -> attrValue = it._6Win.toString()
                        "_7Pick" -> attrValue = it._7Pick.toString()
                        "_7Win" -> attrValue = it._7Win.toString()
                        "_8Pick" -> attrValue = it._8Pick.toString()
                        "_8Win" -> attrValue = it._8Win.toString()
                        "turboWinrate" -> {
                            attrValue = String.format("%.4f", it.turboWins.toFloat() / it.turboPicks.toFloat())
                        }
                        "proWinrate" -> {
                            attrValue = String.format("%.4f", it.proWin.toFloat() / it.proPick.toFloat())
                        }
                        "_1Winrate" -> {
                            attrValue = String.format("%.4f", it._1Win.toFloat() / it._1Pick.toFloat())
                        }
                        "_2Winrate" -> {
                            attrValue = String.format("%.4f", it._2Win.toFloat() / it._2Pick.toFloat())
                        }
                        "_3Winrate" -> {
                            attrValue = String.format("%.3f", it._3Win.toFloat() / it._3Pick.toFloat())
                        }
                        "_4Winrate" -> {
                            attrValue = String.format("%.4f", it._4Win.toFloat() / it._4Pick.toFloat())
                        }
                        "_5Winrate" -> {
                            attrValue = String.format("%.4f", it._5Win.toFloat() / it._5Pick.toFloat())
                        }
                        "_6Winrate" -> {
                            attrValue = String.format("%.4f", it._6Win.toFloat() / it._6Pick.toFloat())
                        }
                        "_7Winrate" -> {
                            attrValue = String.format("%.4f", it._7Win.toFloat() / it._7Pick.toFloat())
                        }
                        "_8Winrate" -> {
                            attrValue = String.format("%.4f", it._8Win.toFloat() / it._8Pick.toFloat())
                        }
                    }

                    if (attrValue != "") {
                        item {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(rowBackgroundColor)
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
                                    .padding(start = 20.dp, end = 20.dp)
                                    .height(60.dp)
                                    .clickable {
                                        onHeroClick.invoke(it)
                                    }

                            ) {
                                Row(
                                    modifier = Modifier
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .width(20.dp)
                                            .height(20.dp)
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxSize()
                                        ) {
                                            Image(
                                                painter = painterResource(R.drawable.ic_comparing_gr),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .width(16.dp)
                                                    .height(15.dp)
                                            )

                                        }

                                        Image(
                                            modifier = Modifier
                                                .width(20.dp)
                                                .fillMaxSize(),
                                            painter = rememberImagePainter(it.icon),
                                            contentDescription = it.name
                                        )

                                    }

                                    Text(
                                        modifier = Modifier
                                            .padding(start = 10.dp),
                                        fontSize = 12.sp,
                                        lineHeight = 50.sp,
                                        color = rowTextColor,
                                        text = it.localizedName
                                    )

                                }
                                Text(
                                    modifier = Modifier,
                                    fontSize = 12.sp,
                                    lineHeight = 50.sp,
                                    color = rowTextColor,
                                    text = attrValue
                                )

                            }

                        }
                    }
                }
            }
        }

    }


}