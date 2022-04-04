package ru.ratatoskr.project_3.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.states.HeroesListState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel

@ExperimentalFoundationApi
@Composable
fun HeroesByAttributeScreen(
    attr: String,
    id: String,
    viewModel: HeroesListViewModel,
    navController: NavController
) {
    val viewState = viewModel.heroesListState.observeAsState()

    when (val state = viewState.value) {
        is HeroesListState.LoadedHeroesListState<*> -> HeroesByAttributeListView(
            attr,
            id,
            state.heroes,
            state.isSortAsc,
            navController,
            {
                navController.navigate(Screens.Hero.route + "/" + it.id)
            },
            {
                android.util.Log.e("TOHA_test","when")
                viewModel.switchAttrSortDirection(attr,it)
            })
        is HeroesListState.NoHeroesListState -> MessageView("Heroes not found")
        is HeroesListState.LoadingHeroesListState -> LoadingView("Heroes loading...")
        is HeroesListState.ErrorHeroesListState -> MessageView("Heroes error!")
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesByAttr(attr)
    })
}


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

    when (attr) {

        "baseHealth" -> titleValue = "Health"
        "baseHealthRegen" -> titleValue = "Health regen"
        "baseMana" -> titleValue = "Mana"
        "baseManaRegen" -> titleValue = "Mana regen"
        "baseArmor" -> titleValue = "Armor"
        "baseMr" -> titleValue = "Magic Resistance"
        "baseStr" -> titleValue = "Base Strength"
        "baseAgi" -> titleValue = "Base Agility"
        "baseInt" -> titleValue = "Base Intelligence"
        "strGain" -> titleValue = "Strength gain"
        "agiGain" -> titleValue = "Agility gain"
        "intGain" -> titleValue = "Intelligence gain"
        "attackRange" -> titleValue = "Attack range"
        "projectileSpeed" -> titleValue = "Projectile speed"
        "attackRate" -> titleValue = "Attack rate"
        "moveSpeed" -> titleValue = "Move speed"
        "cmEnabled" -> titleValue = "Captains mode enabled"
        "legs" -> titleValue = "Legs"
        "turboPicks" -> titleValue = "Turbo pics"
        "turboWins" -> titleValue = "Turbo wins"
        "proBan" -> titleValue = "Pro bans"
        "proWin" -> titleValue = "Pro wins"
        "proPick" -> titleValue = "Pro picks"
        "_1Pick" -> titleValue = "Herald"
        "_1Win" -> titleValue = "Herald"
        "_2Pick" -> titleValue = "Guardian"
        "_2Win" -> titleValue = "Guardian"
        "_3Pick" -> titleValue = "Crusader"
        "_3Win" -> titleValue = "Crusader"
        "_4Pick" -> titleValue = "Archon"
        "_4Win" -> titleValue = "Archon"
        "_5Pick" -> titleValue = "Legend"
        "_5Win" -> titleValue = "Legend"
        "_6Pick" -> titleValue = "Ancient"
        "_6Win" -> titleValue = "Ancient"
        "_7Pick" -> titleValue = "Divine"
        "_7Win" -> titleValue = "Divine"
        "_8Pick" -> titleValue = "Immortal"
        "_8Win" -> titleValue = "Immortal"
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

                                .size(70.dp)
                                .background(Color.Transparent)
                                .border(1.dp, Color(0x880d111c), CircleShape)
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

/*
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    )
                )
            ),
        cells = GridCells.Fixed(count = 1),
        content = {
            heroes.forEach {

                var attrValue = ""

                when (attr) {
                    "legs" -> attrValue = it.legs.toString()
                    "baseHealth" -> attrValue = it.baseHealth.toString()
                    "baseHealthRegen" -> attrValue = "+"+it.baseHealthRegen.toString()
                    "baseMana" -> attrValue = it.baseMana.toString()
                    "baseManaRegen" -> attrValue = "+"+it.baseManaRegen.toString()
                    "baseArmor" -> attrValue = it.baseArmor.toString()
                    "baseMr" -> attrValue = it.baseMr.toString()+"%"
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
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(1.dp)
                                .background(Color.Black)
                                .clickable {
                                    onHeroClick.invoke(it)
                                }
                        ) {
                            Row(){
                                Image(
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(30.dp),
                                    painter = rememberImagePainter(it.icon),
                                    contentDescription = it.name
                                )
                                Text(modifier = Modifier.padding(10.dp), lineHeight = 50.sp, color = Color.White, text = it.localizedName)

                            }

                            Text(modifier = Modifier.padding(10.dp), lineHeight = 50.sp, color = Color.White, text = attrValue)
                        }

                    }
                }
            }
        })
*/
}
