package ru.ratatoskr.project_3.presentation.screens.attribute.views

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.utils.rememberForeverLazyListState

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
    val heroes = data.mapNotNull { it as? Hero }
    var scrollState = rememberForeverLazyListState(key = "Attr_" + attr)
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
    var listColumnsCount = 4
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            listColumnsCount = 7
        }
    }
    var titleValue = ""

    val attrsLanguageMap: Map<String, Int> =
        mapOf(
            "baseHealth" to R.string.base_health_attr,
            "baseMana" to R.string.base_mana_attr,
            "baseHealthRegen" to R.string.base_health_regen_attr,
            "baseManaRegen" to R.string.base_mana_regen_attr,
            "baseArmor" to R.string.base_armor_attr,
            "baseStr" to R.string.base_str_attr,
            "baseAgi" to R.string.base_agi_attr,
            "baseInt" to R.string.base_int_attr,
            "strGain" to R.string.str_gain_attr,
            "agiGain" to R.string.agi_gain_attr,
            "intGain" to R.string.int_gain_attr,
            "attackRange" to R.string.attack_range_attr,
            "projectileSpeed" to R.string.projectile_speed_attr,
            "attackRate" to R.string.attack_rate_attr,
            "moveSpeed" to R.string.move_speed_attr,
            "turboPicks" to R.string.turbo_picks_attr,
            "turboWins" to R.string.turbo_wins_attr,
            "proBan" to R.string.pro_ban_attr,
            "proWin" to R.string.pro_win_attr,
            "proPick" to R.string.pro_pick_attr,
            "_1Pick" to R.string._1_pick_attr,
            "_1Win" to R.string._1_win_attr,
            "_2Pick" to R.string._2_pick_attr,
            "_2Win" to R.string._2_win_attr,
            "_3Pick" to R.string._3_pick_attr,
            "_3Win" to R.string._3_win_attr,
            "_4Pick" to R.string._4_pick_attr,
            "_4Win" to R.string._4_win_attr,
            "_5Pick" to R.string._5_pick_attr,
            "_5Win" to R.string._5_win_attr,
            "_6Pick" to R.string._6_pick_attr,
            "_6Win" to R.string._6_win_attr,
            "_7Pick" to R.string._7_pick_attr,
            "_7Win" to R.string._7_win_attr,
            "_8Pick" to R.string._8_pick_attr,
            "_8Win" to R.string._8_win_attr

        )

    titleValue = stringResource(attrsLanguageMap[attr]!!)

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
                .background(Color(0x55202020))
        ) {

            stickyHeader {

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
                                        .width(13.dp)
                                        .height(13.dp),
                                    painter = rememberImagePainter(
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

                                .width(20.dp)
                                .background(Color.Transparent)
                                .clickable {
                                    onSortChange(!isSortAsc)
                                }
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
            }

            heroes.forEach {

                var attrValue = ""
                var rowBackgroundColor = Color.Transparent
                var rowTextColor = Color.White
                if(id.toInt()==it.id) {
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
                }

                if (attrValue != "") {
                    item {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(rowBackgroundColor)
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
                                .padding(start = 20.dp, end = 20.dp)
                                .height(60.dp)
                                .clickable {
                                    onHeroClick.invoke(it)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                            ) {
                                Image(
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp),
                                    painter = rememberImagePainter(it.icon),
                                    contentDescription = it.name
                                )
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