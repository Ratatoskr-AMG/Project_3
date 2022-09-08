package ru.ratatoskr.doheco.presentation.screens.comparing

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.utils.appUtilsArrays
import ru.ratatoskr.doheco.domain.utils.rememberForeverLazyListState
import ru.ratatoskr.doheco.presentation.screens.comparing.models.ComparingState
import ru.ratatoskr.doheco.presentation.theme.*

@ExperimentalFoundationApi
@Composable
fun ComparingScreen(
    viewModel: ComparingViewModel
) {
    val viewState = viewModel.comparingState.observeAsState()
    var selectMode by remember { mutableStateOf(false) }
    var leftSelected by remember { mutableStateOf(false) }
    var rightSelected by remember { mutableStateOf(false) }


    when (val state = viewState.value) {
        is ComparingState.HeroesState -> {
            var left by remember { mutableStateOf(state.left) }
            var right by remember { mutableStateOf(state.right) }
            Log.e("TOHAht", state.left.toString())
            Log.e("TOHAht", state.right.toString())
            HeroesView(
                {
                    rightSelected = false
                    selectMode = true
                    leftSelected = true
                },
                {
                    leftSelected = false
                    selectMode = true
                    rightSelected = true
                },
                {
                    if (leftSelected) {
                        left = it
                    }
                    if (rightSelected) {
                        right = it
                    }
                    selectMode = false
                    leftSelected = false
                    rightSelected = false
                },
                selectMode,
                leftSelected,
                rightSelected,
                left,
                right,
                state.heroes
            )
        }
        is ComparingState.LoadingState -> {
            Log.e("TOHAht", "LoadingState")
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.setHeroesState()
    })
}

@Composable
fun HeroesView(
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onHeroClick: (Hero) -> Unit,
    selectMode: Boolean,
    leftSelected: Boolean,
    rightSelected: Boolean,
    left: Hero,
    right: Hero,
    heroes: List<Hero>,
) {
    var scrollState = rememberForeverLazyListState(key = "ComparingHeroesList")
    var cAlignLeft = appUtilsArrays.heroImgContentAlign(left)
    var cAlignRight = appUtilsArrays.heroImgContentAlign(right)
    val attrsLanguageMap = appUtilsArrays.attrsLanguageMap()
    var leftImgColor = Color(0xFF00821d)
    var rightImgColor = Color(0xFF6a36a3)

    if (leftSelected == true) {
        leftImgColor = Color(0xFFc98000)
    }
    if (rightSelected == true) {
        rightImgColor = Color(0xFFc98000)
    }
    val listColumnsCount = 4

    Column {
        appHeaderInner {
            appHeaderUnderlinedCenterVerticalRow(Arrangement.SpaceBetween) {
                appHeaderImageBox {
                    appHeaderImage(
                        onLeftClick,
                        left.img,
                        left.localizedName,
                        cAlignLeft,
                        leftImgColor
                    )
                }
                Column(
                    modifier = Modifier
                        .width(180.dp)
                        .background(Color.Black)
                ) {
                    appHeaderBaseText(left.localizedName)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(11.dp)
                            .background(Color.Black)
                            .border(5.dp, Color.Black)
                    )
                    appHeaderBaseText(right.localizedName)
                }
                appHeaderImageBox {
                    appHeaderImage(
                        onRightClick,
                        right.img,
                        right.localizedName,
                        cAlignRight,
                        rightImgColor
                    )
                }
            }
        }
        if (selectMode == false) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
            ) {
                item {
                    ComparingRow(
                        left.baseHealth.toString(),
                        right.baseHealth.toString(),
                        attrsLanguageMap["baseHealth"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.baseMana.toString(),
                        right.baseMana.toString(),
                        attrsLanguageMap["baseMana"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.baseHealthRegen.toString(),
                        right.baseHealthRegen.toString(),
                        attrsLanguageMap["baseHealthRegen"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.baseManaRegen.toString(),
                        right.baseManaRegen.toString(),
                        attrsLanguageMap["baseManaRegen"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.baseArmor.toString(),
                        right.baseArmor.toString(),
                        attrsLanguageMap["baseArmor"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.baseStr.toString(),
                        right.baseStr.toString(),
                        attrsLanguageMap["baseStr"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.baseAgi.toString(),
                        right.baseAgi.toString(),
                        attrsLanguageMap["baseAgi"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.baseInt.toString(),
                        right.baseInt.toString(),
                        attrsLanguageMap["baseInt"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.strGain.toString(),
                        right.strGain.toString(),
                        attrsLanguageMap["strGain"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.agiGain.toString(),
                        right.agiGain.toString(),
                        attrsLanguageMap["agiGain"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.intGain.toString(),
                        right.intGain.toString(),
                        attrsLanguageMap["intGain"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.attackRange.toString(),
                        right.attackRange.toString(),
                        attrsLanguageMap["attackRange"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.projectileSpeed.toString(),
                        right.projectileSpeed.toString(),
                        attrsLanguageMap["projectileSpeed"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.attackRate.toString(),
                        right.attackRate.toString(),
                        attrsLanguageMap["attackRate"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.moveSpeed.toString(),
                        right.moveSpeed.toString(),
                        attrsLanguageMap["moveSpeed"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.turboPicks.toString(),
                        right.turboPicks.toString(),
                        attrsLanguageMap["turboPicks"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.turboWins.toString(),
                        right.turboWins.toString(),
                        attrsLanguageMap["turboWins"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.proBan.toString(),
                        right.proBan.toString(),
                        attrsLanguageMap["proBan"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.proPick.toString(),
                        right.proPick.toString(),
                        attrsLanguageMap["proPick"]!!
                    )
                }
                item {
                    ComparingRow(
                        left.proWin.toString(),
                        right.proWin.toString(),
                        attrsLanguageMap["proWin"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._1Pick.toString(),
                        right._1Pick.toString(),
                        attrsLanguageMap["_1Pick"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._1Win.toString(),
                        right._1Win.toString(),
                        attrsLanguageMap["_1Win"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._2Pick.toString(),
                        right._2Pick.toString(),
                        attrsLanguageMap["_2Pick"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._2Win.toString(),
                        right._2Win.toString(),
                        attrsLanguageMap["_2Win"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._3Pick.toString(),
                        right._3Pick.toString(),
                        attrsLanguageMap["_3Pick"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._3Win.toString(),
                        right._3Win.toString(),
                        attrsLanguageMap["_3Win"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._4Pick.toString(),
                        right._4Pick.toString(),
                        attrsLanguageMap["_4Pick"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._4Win.toString(),
                        right._4Win.toString(),
                        attrsLanguageMap["_4Win"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._5Pick.toString(),
                        right._5Pick.toString(),
                        attrsLanguageMap["_5Pick"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._5Win.toString(),
                        right._5Win.toString(),
                        attrsLanguageMap["_5Win"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._6Pick.toString(),
                        right._6Pick.toString(),
                        attrsLanguageMap["_6Pick"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._6Win.toString(),
                        right._6Win.toString(),
                        attrsLanguageMap["_6Win"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._7Pick.toString(),
                        right._7Pick.toString(),
                        attrsLanguageMap["_7Pick"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._7Win.toString(),
                        right._7Win.toString(),
                        attrsLanguageMap["_7Win"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._8Pick.toString(),
                        right._8Pick.toString(),
                        attrsLanguageMap["_8Pick"]!!
                    )
                }
                item {
                    ComparingRow(
                        left._8Win.toString(),
                        right._8Win.toString(),
                        attrsLanguageMap["_8Win"]!!
                    )
                }
            }
        } else {

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
            ) {
                var listRowsCount = heroes.size / (listColumnsCount + 1)
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
                                    Box(modifier = Modifier
                                        .clickable {
                                            onHeroClick(hero)
                                        }
                                        .width(70.dp)
                                        .padding(10.dp)
                                        .height(35.dp)) {

                                        Image(
                                            modifier = Modifier
                                                .width(70.dp)
                                                .height(35.dp),
                                            painter = loadPicture(
                                                url = hero.icon,
                                                placeholder = painterResource(id = R.drawable.ic_comparing_gr)
                                            ),
                                            contentDescription = hero.name
                                        )
                                    }

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

@Composable
fun ComparingRow(
    textLeft: String,
    textRight: String,
    textCenterRes: Int
) {
    val configuration = LocalConfiguration.current
    var blockHeight = 40.dp
    val screenWidth = configuration.screenWidthDp
    val valueLeft = textLeft.toFloat()
    val valueRight = textRight.toFloat()
    val summ = valueLeft + valueRight

    Log.e("TOHAcalc textLeft: ", textLeft)

    val raz = summ / screenWidth

    var leftBlockWidthFl = valueLeft / raz
    var rightBlockWidthFl = valueRight / raz

    if (summ == 0f) {
        leftBlockWidthFl = screenWidth / 2.toFloat()
        rightBlockWidthFl = screenWidth / 2.toFloat()
    } else {
        if (leftBlockWidthFl == 0f) leftBlockWidthFl = 8f
        if (rightBlockWidthFl == 0f) rightBlockWidthFl = 8f
    }

    var leftBlockWidth = leftBlockWidthFl.dp
    var rightBlockWidth = rightBlockWidthFl.dp
    var leftBlockColor = Color(0xFF016D19)
    var rightBlockColor = Color(0xFF633397)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
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
                    .border(2.dp, Color.Black)
            )
            Box(
                modifier = Modifier
                    .height(blockHeight)
                    .background(rightBlockColor)
                    .width(rightBlockWidth)
                    .border(2.dp, Color.Black)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(blockHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = textLeft,
                color = Color.White,
                textAlign = TextAlign.Left,
                fontSize = 12.sp
            )
            Box(/*modifier = Modifier
                .background(Color.Black.copy(alpha = 1f))*/) {
                Text(
                    modifier = Modifier
                        .padding(start=20.dp,end=20.dp,top=3.dp,bottom=3.dp),
                    text = stringResource(textCenterRes),
                    textAlign = TextAlign.Center, color = Color.White,
                    fontSize=12.sp
                )
            }
            Text(
                modifier = Modifier.padding(end = 20.dp),
                text = textRight,
                color = Color.White,
                textAlign = TextAlign.Right,
                fontSize = 12.sp
            )
        }
    }
}