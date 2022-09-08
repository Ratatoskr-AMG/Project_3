package ru.ratatoskr.doheco.presentation.screens.recommendations.views

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.utils.rememberForeverLazyListState
import ru.ratatoskr.doheco.presentation.screens.recommendations.RecommendationsViewModel
import ru.ratatoskr.doheco.presentation.theme.*


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalFoundationApi
@Composable
fun RecommendationsView(
    viewModel: RecommendationsViewModel,
    imageLoader: ImageLoader,
    data: List<Any?>,
    player_tier: String,
    onHeroClick: (Hero) -> Unit,
    onTierImgClick: () -> Unit
) {

    var tierImgAddr = "http://ratatoskr.ru/app/img/tier/0.png"
    var tierBlockNum = 0;

    if (player_tier != "undefined") {
        tierImgAddr = "http://ratatoskr.ru/app/img/tier/" + player_tier + ".png"
        tierBlockNum = player_tier.toInt()
        Log.e("TOHAtier","player_tier:"+player_tier)
        Log.e("TOHAtier","tierBlockNum:"+tierBlockNum.toString())
    }
    val heroes = data.mapNotNull { it as? Hero }

    var allHeroesBase = heroes.toMutableList()
    var top10ProWinsOnProPicksBase =
        allHeroesBase.sortedBy { it.proWin.toFloat() / it.proPick.toFloat() }
    var top10ProWinsOnProPicks = top10ProWinsOnProPicksBase.reversed().slice(0..14)

    //Log.e("TOHAcomp","heroes_base:"+heroes_base.toString())
    //Log.e("TOHAcomp","asdad:"+asdad.toString())
    //Log.e("TOHAcomp","heroes:"+heroes.toString())

    var offsetPosition by remember { mutableStateOf(0f) }
    var searchState by remember { mutableStateOf(TextFieldValue("", selection = TextRange.Zero)) }
    val focusRequesterTop = remember { FocusRequester() }
    var scrollState = rememberForeverLazyListState(key = "Recommendations")
    var listColumnsCount = 4
    var listRowsCount = top10ProWinsOnProPicks.size / (listColumnsCount + 1)
    if (top10ProWinsOnProPicks.size % (listColumnsCount + 1) > 0) {
        listRowsCount += 1
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = consumed.y
                offsetPosition += delta
                return Offset.Zero
            }
        }
    }

    Column {
        appHeaderInner {
            appHeaderUnderlinedCenterVerticalRow(Arrangement.Start) {
                appHeaderImageBox {
                    appHeaderImage(
                        {onTierImgClick()},
                        tierImgAddr,
                        "Tier",
                        Alignment.Center,
                        Color(0x880d111c)
                    )
                }
                appHeaderLeftImgText(stringResource(id = R.string.recommendations))
            }
        }
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Transparent
                            )
                        )
                    )
                    .fillMaxSize()
            ) {
                //Tier Wins/Picks Block
                if (tierBlockNum > 0) {
                    when (tierBlockNum) {
                        1 -> {
                            var top10HeraldWinsOnProPicksBase =
                                allHeroesBase.sortedBy { it._1Win.toFloat() / it._1Pick.toFloat() }
                            var top10HeraldWinsOnProPicks = top10HeraldWinsOnProPicksBase.reversed().slice(0..14)
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_herald_wins_to_pro_picks))
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        top10HeraldWinsOnProPicksBase,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        2 -> {
                            var top10GuardianWinsOnProPicksBase =
                                allHeroesBase.sortedBy { it._2Win.toFloat() / it._2Pick.toFloat() }
                            var top10GuardianWinsOnProPicks = top10GuardianWinsOnProPicksBase.reversed().slice(0..14)
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Guardian_wins_to_pro_picks))
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        top10GuardianWinsOnProPicksBase,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        3 -> {
                            var top10CrusaderWinsOnProPicksBase =
                                allHeroesBase.sortedBy { it._3Win.toFloat() / it._3Pick.toFloat() }
                            var top10CrusaderWinsOnProPicks = top10CrusaderWinsOnProPicksBase.reversed().slice(0..14)
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Crusader_wins_to_pro_picks))
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        top10CrusaderWinsOnProPicksBase,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        4 -> {
                            var top10ArchonWinsOnProPicksBase =
                                allHeroesBase.sortedBy { it._4Win.toFloat() / it._4Pick.toFloat() }
                            var top10ArchonWinsOnProPicks = top10ArchonWinsOnProPicksBase.reversed().slice(0..14)
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Archon_wins_to_pro_picks))
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        top10ArchonWinsOnProPicksBase,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        5 -> {
                            var top10LegendWinsOnProPicksBase =
                                allHeroesBase.sortedBy { it._5Win.toFloat() / it._5Pick.toFloat() }
                            var top10LegendWinsOnProPicks = top10LegendWinsOnProPicksBase.reversed().slice(0..14)
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Legend_wins_to_pro_picks))
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        top10LegendWinsOnProPicksBase,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        6 -> {
                            var top10AncientWinsOnProPicksBase =
                                allHeroesBase.sortedBy { it._6Win.toFloat() / it._6Pick.toFloat() }
                            var top10AncientWinsOnProPicks = top10AncientWinsOnProPicksBase.reversed().slice(0..14)
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Ancient_wins_to_pro_picks))
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        top10AncientWinsOnProPicksBase,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        7 -> {
                            var top10DivineWinsOnProPicksBase =
                                allHeroesBase.sortedBy { it._7Win.toFloat() / it._7Pick.toFloat() }
                            var top10DivineWinsOnProPicks = top10DivineWinsOnProPicksBase.reversed().slice(0..14)
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Divine_wins_to_pro_picks))
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        top10DivineWinsOnProPicksBase,
                                        listColumnsCount,
                                        row,
                                        onHeroClick
                                    )
                                }
                            }
                        }
                        8 -> {
                            var top10ImmortalWinsOnProPicksBase =
                                allHeroesBase.sortedBy { it._8Win.toFloat() / it._8Pick.toFloat() }
                            var top10ImmortalWinsOnProPicks = top10ImmortalWinsOnProPicksBase.reversed().slice(0..14)
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_Immortal_wins_to_pro_picks))
                            }
                            for (row in 0 until listRowsCount) {
                                item {
                                    recommendationsHeroesBlock(
                                        top10ImmortalWinsOnProPicksBase,
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
                    recommendationsTitleBlock(stringResource(id = R.string.top_Pro_wins_to_pro_picks))
                }
                for (row in 0 until listRowsCount) {
                    item {
                        recommendationsHeroesBlock(
                            top10ProWinsOnProPicks,
                            listColumnsCount,
                            row,
                            onHeroClick
                        )
                    }
                }
                //Best of Agility Wins/Picks Block
            }
        }

    }
    LaunchedEffect(Unit) {

    }
}

@Composable
fun recommendationsTitleBlock(text: String) {
    Box(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .background(Color(0xFF131313))
    ) {

        Text(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 20.dp,
                    end = 20.dp
                )
                .fillMaxWidth(),
            color = Color.White,
            fontSize = 12.sp,
            text = text
        )
    }
}

@Composable
fun recommendationsHeroesBlock(
    heroes: List<Hero>,
    listColumnsCount: Int,
    row: Int,
    onHeroClick: (Hero) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (column in 0..listColumnsCount) {
            var index = column + (row * (listColumnsCount + 1))
            if (index <= heroes.size - 1) {
                var hero = heroes.get(index)
                Box(modifier = Modifier
                    .clickable {
                        onHeroClick(hero)
                    }
                    .width(70.dp)
                    .padding(10.dp)
                    .height(35.dp)) {
                    Image(
                        contentDescription = hero.name,
                        painter = loadPicture(
                            url = hero.icon,
                            placeholder = painterResource(id = R.drawable.ic_comparing_gr)
                        ),
                        modifier = Modifier
                            .width(70.dp)
                            .height(35.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .padding(10.dp)
                        .height(35.dp)
                )
            }
        }


    }
}