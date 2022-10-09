package ru.ratatoskr.doheco.presentation.screens.role.views

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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.utils.appUtilsArrays
import ru.ratatoskr.doheco.domain.utils.rememberForeverLazyListState
import ru.ratatoskr.doheco.presentation.screens.attribute.views.roleRecommendationsHeroesBlock
import ru.ratatoskr.doheco.presentation.screens.attribute.views.roleRecommendationsTitleBlock
import ru.ratatoskr.doheco.presentation.screens.home.views.heroesListItemBox
import ru.ratatoskr.doheco.presentation.screens.recommendations.views.recommendationsHeroesBlock
import ru.ratatoskr.doheco.presentation.screens.recommendations.views.recommendationsTitleBlock
import ru.ratatoskr.doheco.presentation.theme.appHeaderImage
import ru.ratatoskr.doheco.presentation.theme.appHeaderImageBox

@ExperimentalFoundationApi
@Composable
fun RoleListView(
    role: String,
    player_tier: String,
    heroes: List<Any?>,
    favoriteHeroes: List<Hero>,
    navController: NavController,
    onHeroClick: (Hero) -> Unit,
    onTierImgClick: () -> Unit
) {

    var tierImgAddr = "http://ratatoskr.ru/app/img/tier/0.png"
    var tierBlockNum = 0;
    if (player_tier != "undefined") {
        tierImgAddr = "http://ratatoskr.ru/app/img/tier/" + player_tier + ".png"
        tierBlockNum = player_tier.toInt()
    }

    val configuration = LocalConfiguration.current
    val heroes = heroes.mapNotNull { it as? Hero }
    var scrollState = rememberForeverLazyListState(key = "Role_" + role)
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

    var allHeroesBase = heroes.toMutableList()

    var topProWinsOnPicksBase =
        allHeroesBase.sortedBy { it.proWin.toFloat() / it.proPick.toFloat() }
    var topHeraldWinsOnPicksBase =
        allHeroesBase.sortedBy { it._1Win.toFloat() / it._1Pick.toFloat() }
    var topGuardianWinsOnPicksBase =
        allHeroesBase.sortedBy { it._2Win.toFloat() / it._2Pick.toFloat() }
    var topCrusaderWinsOnPicksBase =
        allHeroesBase.sortedBy { it._3Win.toFloat() / it._3Pick.toFloat() }
    var topArchonWinsOnPicksBase =
        allHeroesBase.sortedBy { it._4Win.toFloat() / it._4Pick.toFloat() }
    var topLegendWinsOnPicksBase =
        allHeroesBase.sortedBy { it._5Win.toFloat() / it._5Pick.toFloat() }
    var topAncientWinsOnPicksBase =
        allHeroesBase.sortedBy { it._6Win.toFloat() / it._6Pick.toFloat() }
    var topDivineWinsOnPicksBase =
        allHeroesBase.sortedBy { it._7Win.toFloat() / it._7Pick.toFloat() }
    var topImmortalWinsOnPicksBase =
        allHeroesBase.sortedBy { it._8Win.toFloat() / it._8Pick.toFloat() }
    var topWinsOnPicksBase =
        allHeroesBase.sortedBy {
            (it.turboWins.toFloat() + it.proWin.toFloat() + it._1Win.toFloat() + it._2Win.toFloat() + it._3Win.toFloat() + it._4Win.toFloat() + it._5Win.toFloat() + it._6Win.toFloat() + it._7Win.toFloat() + it._8Win.toFloat()) / it.turboPicks.toFloat() + it.proPick.toFloat() + it._1Pick.toFloat() + it._2Pick.toFloat() + it._3Pick.toFloat() + it._4Pick.toFloat() + it._5Pick.toFloat() + it._6Pick.toFloat() + it._7Pick.toFloat() + it._8Pick.toFloat()
        }
    var topProBansBase = allHeroesBase.sortedBy { it.proBan }

    val topProWinsOnPicks = topProWinsOnPicksBase.reversed().slice(0..14)
    var topHeraldWinsOnPicks = topHeraldWinsOnPicksBase.reversed().slice(0..14)
    var topGuardianWinsOnPicks =
        topGuardianWinsOnPicksBase.reversed().slice(0..14)
    var topCrusaderWinsOnPicks =
        topCrusaderWinsOnPicksBase.reversed().slice(0..14)
    var topArchonWinsOnPicks =
        topArchonWinsOnPicksBase.reversed().slice(0..14)
    var topLegendWinsOnPicks =
        topLegendWinsOnPicksBase.reversed().slice(0..14)
    var topAncientWinsOnPicks =
        topAncientWinsOnPicksBase.reversed().slice(0..14)
    var topDivineWinsOnPicks =
        topDivineWinsOnPicksBase.reversed().slice(0..14)
    var topImmortalWinsOnPicks =
        topImmortalWinsOnPicksBase.reversed().slice(0..14)
    var topWinsOnPicks =
        topWinsOnPicksBase.reversed().slice(0..14)
    val topProBans = topProBansBase.reversed().slice(0..14)


    val rolesLanguageMap: Map<String, Int> = appUtilsArrays.rolesLanguageMap()
    val rolesMultipleLanguageMap: Map<String, Int> = appUtilsArrays.rolesMultipleLanguageMap()


    var roleText = if (role in rolesLanguageMap) {
        stringResource(rolesLanguageMap[role]!!)
    } else {
        role
    }

    var roleMultipleText = if (role in rolesMultipleLanguageMap) {
        stringResource(rolesMultipleLanguageMap[role]!!)
    } else {
        role
    }
    var listRowsCount = 3
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color.Black)
        ) {

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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


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
                                    roleMultipleText,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    lineHeight = 20.sp
                                )
                            }

                        }

                        appHeaderImageBox {
                            appHeaderImage(
                                { onTierImgClick() },
                                tierImgAddr,
                                "Tier",
                                Alignment.Center,
                                Color(0x880d111c)
                            )
                        }

                    }
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
                    .background(Color.Black)
                //.background(Color(0x55202020))
            ) {

                //topHeraldWinsOnPicks Block
                if (tierBlockNum > 0) {
                    when (tierBlockNum) {
                        1 -> {
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_herald_wins_to_picks) + " " +
                                        roleMultipleText)
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        topHeraldWinsOnPicks,
                                        favoriteHeroes,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        2 -> {
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Guardian_wins_to_picks) + " " +
                                        roleMultipleText)
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        topGuardianWinsOnPicks,
                                        favoriteHeroes,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        3 -> {

                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Crusader_wins_to_picks) + " " +
                                        roleMultipleText)
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        topCrusaderWinsOnPicks,
                                        favoriteHeroes,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        4 -> {

                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Archon_wins_to_picks) + " " +
                                        roleMultipleText)
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        topArchonWinsOnPicks,
                                        favoriteHeroes,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        5 -> {
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Legend_wins_to_picks) + " " +
                                        roleMultipleText)
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        topLegendWinsOnPicks,
                                        favoriteHeroes,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        6 -> {
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Ancient_wins_to_picks) + " " +
                                        roleMultipleText)
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        topAncientWinsOnPicks,
                                        favoriteHeroes,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        7 -> {
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Divine_wins_to_picks) + " " +
                                        roleMultipleText)
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        topDivineWinsOnPicks,
                                        favoriteHeroes,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        8 -> {
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Immortal_wins_to_picks) + " " +
                                        roleMultipleText)
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        topImmortalWinsOnPicks,
                                        favoriteHeroes,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        else -> {}
                    }
                }
                //Pro Wins/Picks Block
                item {
                    roleRecommendationsTitleBlock(
                        stringResource(id = R.string.top_Pro_wins_to_picks) + " " +
                                roleMultipleText
                    )
                }
                var listRowsCount = 3
                for (row in 0 until listRowsCount) {
                    item {
                        roleRecommendationsHeroesBlock(
                            topProWinsOnPicks,
                            favoriteHeroes,
                            listColumnsCount,
                            row,
                            onHeroClick
                        )
                    }
                }
                //All
                item {
                    roleRecommendationsTitleBlock(stringResource(id = R.string.all_heroes)+" "+roleMultipleText)
                }
                listRowsCount = heroes.size / (listColumnsCount + 1)
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
                                    var favoriteFlag = favoriteHeroes.contains(hero)
                                    heroesListItemBox(onHeroClick,hero,favoriteFlag)
                                    /*
                                    Box(modifier = Modifier
                                        .clickable {
                                            onHeroClick(hero)
                                        }
                                        .width(70.dp)
                                        .padding(10.dp)
                                        .height(35.dp)
                                    ) {

                                        Row(
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
                                        }


                                        Image(
                                            modifier = Modifier
                                                .width(70.dp)
                                                .height(35.dp),
                                            painter = rememberImagePainter(hero.icon),
                                            contentDescription = hero.name
                                        )
                                    }
*/
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


}