package net.doheco.presentation.screens.recommendations.views

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.doheco.R
import net.doheco.domain.model.Hero
import net.doheco.domain.utils.rememberForeverLazyListState
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.theme.*
import net.doheco.shimmerBackground

@ExperimentalFoundationApi
@Composable
fun RecommendationsView(
    heroes: List<Any?>,
    favoriteHeroes: List<Hero>,
    player_tier: String,
    onHeroClick: (Hero) -> Unit,
    onTierImgClick: () -> Unit,
    widthSizeClass: WindowWidthSizeClass,
    onReloadClick: () -> Unit
) {

    var tierImgAddr = "https://doheco.net/app/img/tier/0.png"
    var tierBlockNum = 0;
    if (player_tier != "undefined") {
        tierImgAddr = "https://doheco.net/app/img/tier/${player_tier[0]}.png"
        tierBlockNum = player_tier[0].toString().toInt()
        Log.e("TOHAIMG",tierImgAddr)
    }

    val heroes = heroes.mapNotNull { it as? Hero }
    var allHeroesBase = heroes.toMutableList()

    var topProWinsOnPicksBase =
        allHeroesBase.sortedBy { String.format("%.4f", it.proWin.toFloat() / it.proPick.toFloat()) }
    var topHeraldWinsOnPicksBase =
        allHeroesBase.sortedBy { String.format("%.4f", it._1Win.toFloat() / it._1Pick.toFloat()) }
    var topGuardianWinsOnPicksBase =
        allHeroesBase.sortedBy { String.format("%.4f", it._2Win.toFloat() / it._2Pick.toFloat()) }
    var topCrusaderWinsOnPicksBase =
        allHeroesBase.sortedBy { String.format("%.4f", it._3Win.toFloat() / it._3Pick.toFloat()) }
    var topArchonWinsOnPicksBase =
        allHeroesBase.sortedBy { String.format("%.4f", it._4Win.toFloat() / it._4Pick.toFloat()) }
    var topLegendWinsOnPicksBase =
        allHeroesBase.sortedBy { String.format("%.4f", it._5Win.toFloat() / it._5Pick.toFloat()) }
    var topAncientWinsOnPicksBase =
        allHeroesBase.sortedBy { String.format("%.4f", it._6Win.toFloat() / it._6Pick.toFloat()) }
    var topDivineWinsOnPicksBase =
        allHeroesBase.sortedBy { String.format("%.4f", it._7Win.toFloat() / it._7Pick.toFloat()) }
    var topImmortalWinsOnPicksBase =
        allHeroesBase.sortedBy { String.format("%.4f", it._8Win.toFloat() / it._8Pick.toFloat()) }
    var topWinsOnPicksBase =
        allHeroesBase.sortedBy {
            (String.format(
                "%.4f",
                it.turboWins.toFloat() + it.proWin.toFloat() + it._1Win.toFloat() + it._2Win.toFloat() + it._3Win.toFloat() + it._4Win.toFloat() + it._5Win.toFloat() + it._6Win.toFloat() + it._7Win.toFloat() + it._8Win.toFloat()
             / it.turboPicks.toFloat() + it.proPick.toFloat() + it._1Pick.toFloat() + it._2Pick.toFloat() + it._3Pick.toFloat() + it._4Pick.toFloat() + it._5Pick.toFloat() + it._6Pick.toFloat() + it._7Pick.toFloat() + it._8Pick.toFloat()))
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

    topHeraldWinsOnPicks.forEach {
        Log.e(
            "TOHAtop",
            it.localizedName + ":" + it._1Win + "/" + it._1Pick + "=" + it._1Win.toFloat() / it._1Pick.toFloat()
        )
    }

    var offsetPosition by remember { mutableStateOf(0f) }
    var scrollState = rememberForeverLazyListState(key = "Recommendations")

    val listColumnsCount = if (widthSizeClass == WindowWidthSizeClass.Expanded ||
        widthSizeClass == WindowWidthSizeClass.Medium) 9 else 4

    var listRowsCount = topProWinsOnPicks.size / (listColumnsCount + 1)
    if (topProWinsOnPicks.size % (listColumnsCount + 1) > 0) {
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
            Row( modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 45.dp, bottom = 20.dp)) {
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(70.dp)
                    ) {
                        appHeaderImage(
                            { onTierImgClick() },
                            tierImgAddr,
                            "Tier",
                            Alignment.Center,
                            Color(0x880d111c)
                        )
                    }
                    appHeaderLeftImgText(stringResource(id = R.string.recommendations))

                }
                Box(modifier = Modifier.padding(start = 0.dp, end = 0.dp, top = 45.dp, bottom = 20.dp)) {
                    Image(
                        painter = painterResource(R.drawable.ic_baseline_refresh_24),
                        contentDescription = stringResource(id = R.string.reload),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                            .border(1.dp, Color(0x880d111c), CircleShape)
                            .clickable { onReloadClick() }
                    )
                }
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
                            item {
                                recommendationsTitleBlock(stringResource(id = R.string.top_herald_wins_to_picks))
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
                                recommendationsTitleBlock(stringResource(id = R.string.top_Guardian_wins_to_picks))
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
                                recommendationsTitleBlock(stringResource(id = R.string.top_Crusader_wins_to_picks))
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
                                recommendationsTitleBlock(stringResource(id = R.string.top_Archon_wins_to_picks))
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
                                recommendationsTitleBlock(stringResource(id = R.string.top_Legend_wins_to_picks))
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
                                recommendationsTitleBlock(stringResource(id = R.string.top_Ancient_wins_to_picks))
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
                                recommendationsTitleBlock(stringResource(id = R.string.top_Divine_wins_to_picks))
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
                                recommendationsTitleBlock(stringResource(id = R.string.top_Immortal_wins_to_picks))
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
                //Total Wins/Picks Block
                item {
                    recommendationsTitleBlock(stringResource(id = R.string.top_wins_to_picks))
                }
                for (row in 0 until listRowsCount) {
                    item {
                        recommendationsHeroesBlock(
                            topWinsOnPicks,
                            favoriteHeroes,
                            listColumnsCount,
                            row,
                            onHeroClick
                        )
                    }
                }
                //Pro Wins/Picks Block
                item {
                    recommendationsTitleBlock(stringResource(id = R.string.top_Pro_wins_to_picks))
                }
                for (row in 0 until listRowsCount) {
                    item {
                        recommendationsHeroesBlock(
                            topProWinsOnPicks,
                            favoriteHeroes,
                            listColumnsCount,
                            row,
                            onHeroClick
                        )
                    }
                }
                //Pro Bans Block
                /*
                item {
                    recommendationsTitleBlock(stringResource(id = R.string.top_pro_bans))
                }
                for (row in 0 until listRowsCount) {
                    item {
                        recommendationsHeroesBlock(
                            topProBans,
                            favoriteHeroes,
                            listColumnsCount,
                            row,
                            onHeroClick
                        )
                    }
                }
                */
            }
        }

    }
    LaunchedEffect(Unit) {

    }
}

@Composable
fun RecommendationsViewLoading(
    profileViewModel: ProfileViewModel,
    onReloadClick: () -> Unit,
    ) {

    Column {
        appHeaderInner {
            Row( modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 45.dp, bottom = 20.dp)) {
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(70.dp)
                            .shimmerBackground(CircleShape)
                    )
                    appHeaderLeftImgText(stringResource(id = R.string.recommendations))

                }
                Box(modifier = Modifier.padding(start = 0.dp, end = 0.dp, top = 45.dp, bottom = 20.dp)) {
                    Image(
                        painter = painterResource(R.drawable.ic_baseline_refresh_24),
                        contentDescription = stringResource(id = R.string.reload),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                            .border(1.dp, Color(0x880d111c), CircleShape)
                            .clickable { onReloadClick() }
                    )
                }
            }



        }
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                //Tier Wins/Picks Block
                item{
                    recommendationsTitleBlockLoading()
                    Box(Modifier.fillMaxWidth().height(170.dp).padding(start=10.dp,end=10.dp,bottom=10.dp).shimmerBackground(
                        RoundedCornerShape(5.dp)
                    ))
                }
                item{
                    recommendationsTitleBlockLoading()

                    Box(Modifier.fillMaxWidth().height(170.dp).padding(start=10.dp,end=10.dp,bottom=10.dp).shimmerBackground(
                        RoundedCornerShape(5.dp)
                    ))
                }
                item{
                    recommendationsTitleBlockLoading()

                    Box(Modifier.fillMaxWidth().height(170.dp).padding(start=10.dp,end=10.dp,bottom=10.dp).shimmerBackground(
                        RoundedCornerShape(5.dp)
                    ))
                }

            }
        }

    }

}



