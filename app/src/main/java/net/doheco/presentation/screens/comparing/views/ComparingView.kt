package net.doheco.presentation.screens.comparing.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.doheco.domain.model.Hero
import net.doheco.domain.utils.appUtilsArrays
import net.doheco.domain.utils.rememberForeverLazyListState
import net.doheco.presentation.base.heroesRoles
import net.doheco.presentation.screens.comparing.models.ComparingRow
import net.doheco.presentation.screens.home.views.heroesListItemBox
import net.doheco.presentation.theme.*
import net.doheco.presentation.base.infoBlocks
import net.doheco.presentation.screens.comparing.models.ComparingRoleRow
import net.doheco.R

@ExperimentalFoundationApi
@Composable
fun ComparingView(
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onHeroClick: (Hero) -> Unit,
    onHeroInfoBlockSelect: (String) -> Unit,
    selectMode: Boolean,
    leftSelected: Boolean,
    rightSelected: Boolean,
    left: Hero,
    right: Hero,
    heroes: List<Hero>,
    favoriteHeroes: List<Hero>,
    currentInfoBlock: String,
    widthSizeClass: WindowWidthSizeClass
) {
    var scrollState = rememberForeverLazyListState(key = "ComparingHeroesList")
    var cAlignLeft = appUtilsArrays.heroImgContentAlign(left)
    var cAlignRight = appUtilsArrays.heroImgContentAlign(right)
    val attrsLanguageMap = appUtilsArrays.attrsLanguageMap()
    val rolesLanguageMap = appUtilsArrays.rolesLanguageMap()
    var leftImgColor = Color(0xFF00821d)
    var rightImgColor = Color(0xFF6a36a3)
    var leftText = left.localizedName
    var rightText = right.localizedName
    val gson = GsonBuilder().create()

    if (leftSelected) {
        //leftImgColor = Color(0xFFc98000)
        leftImgColor = Color(0xFF000000)
        leftText = stringResource(id = R.string.choose)
    }
    if (rightSelected) {
        //rightImgColor = Color(0xFFc98000)
        rightImgColor = Color(0xFF000000)
        rightText = stringResource(id = R.string.choose)
    }
    val listColumnsCount = if (widthSizeClass == WindowWidthSizeClass.Expanded ||
        widthSizeClass == WindowWidthSizeClass.Medium) 9 else 4

    var imgAddrLeft = left.img

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        appHeaderInner {
            appHeaderUnderlinedCenterVerticalRow(Arrangement.SpaceBetween) {
                appHeaderImageBox {
                    if (leftSelected) {
                        appHeaderImage(
                            onLeftClick,
                            "",
                            "",
                            Alignment.Center,
                            leftImgColor,
                            painterResource(id = R.drawable.ic_comparing_gr)
                        )
                    } else {
                        appHeaderImage(
                            onLeftClick,
                            imgAddrLeft,
                            left.localizedName,
                            cAlignLeft,
                            leftImgColor
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .width(180.dp)
                        .background(Color.Black)
                ) {

                    appHeaderBaseText(leftText)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(11.dp)
                            .background(Color.Black)
                            .border(5.dp, Color.Black)
                    )
                    appHeaderBaseText(rightText)
                }
                appHeaderImageBox {
                    if (rightSelected) {
                        appHeaderImage(
                            onLeftClick,
                            "",
                            "",
                            Alignment.Center,
                            rightImgColor,
                            painterResource(id = R.drawable.ic_comparing_gr)
                        )
                    } else {
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
        }
        if (!selectMode) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
            ) {
                stickyHeader {
                    LazyRow(
                        modifier = Modifier
                            .layoutId("hero_roles_lazy_row")
                            .fillMaxSize()
                            .padding(top = 0.dp, start = 2.dp, end = 2.dp, bottom = 2.dp)
                            //.background(Color(0xFF131313))
                            .background(Color(0xFF131313))
                    ) {
                        items(infoBlocks.size) { id ->
                            var infoBlockName = infoBlocks[id]
                            var infoBlockNameTitle = stringResource(id = R.string.picks)

                            if(id==1){
                                infoBlockNameTitle = stringResource(id = R.string.wins)
                            }
                            if(id==2){
                                infoBlockNameTitle = stringResource(id = R.string.winrates)
                            }
                            if(id==3){
                                infoBlockNameTitle = stringResource(id = R.string.properties)
                            }
                            if(id==4){
                                infoBlockNameTitle = stringResource(id = R.string.roles)
                            }
                            Box(
                                modifier = Modifier
                                    .padding(top = 0.dp, bottom = 0.dp, start = 0.dp)
                                    //.border(1.dp, Color.White, CircleShape)
                                    .background(Color.Transparent)
                                    .clickable {
                                        onHeroInfoBlockSelect(infoBlockName)
                                    }
                            ) {
                                var backgroundColor =
                                    if (currentInfoBlock == infoBlockName) Color(0xFFc98000) else Color(
                                        0xFF131313
                                    )
                                var textColor =
                                    if (currentInfoBlock == infoBlockName) Color(0xFF000000) else Color(
                                        0xFFFFFFFF
                                    )
                                Box(
                                    modifier = Modifier
                                        .background(backgroundColor)
                                        .padding(
                                            top = 10.dp,
                                            bottom = 10.dp,
                                            start = 20.dp,
                                            end = 20.dp
                                        )

                                ) {
                                    Text(
                                        fontSize = 12.sp,
                                        color = textColor,
                                        text = infoBlockNameTitle
                                    )
                                }

                            }
                        }
                    }

                }
                if (currentInfoBlock == "Picks") {
                    item {
                        ComparingRow(
                            left.turboPicks.toString(),
                            right.turboPicks.toString(),
                            attrsLanguageMap["turboPicks"]!!
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
                            left._2Pick.toString(),
                            right._2Pick.toString(),
                            attrsLanguageMap["_2Pick"]!!
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
                            left._4Pick.toString(),
                            right._4Pick.toString(),
                            attrsLanguageMap["_4Pick"]!!
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
                            left._6Pick.toString(),
                            right._6Pick.toString(),
                            attrsLanguageMap["_6Pick"]!!
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
                            left._8Pick.toString(),
                            right._8Pick.toString(),
                            attrsLanguageMap["_8Pick"]!!
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
                            left.proBan.toString(),
                            right.proBan.toString(),
                            attrsLanguageMap["proBan"]!!
                        )
                    }
                }
                if (currentInfoBlock == "Wins") {
                    item {
                        ComparingRow(
                            left.turboWins.toString(),
                            right.turboWins.toString(),
                            attrsLanguageMap["turboWins"]!!
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
                            left._2Win.toString(),
                            right._2Win.toString(),
                            attrsLanguageMap["_2Win"]!!
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
                            left._4Win.toString(),
                            right._4Win.toString(),
                            attrsLanguageMap["_4Win"]!!
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
                            left._6Win.toString(),
                            right._6Win.toString(),
                            attrsLanguageMap["_6Win"]!!
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
                            left._8Win.toString(),
                            right._8Win.toString(),
                            attrsLanguageMap["_8Win"]!!
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
                            left.proBan.toString(),
                            right.proBan.toString(),
                            attrsLanguageMap["proBan"]!!
                        )
                    }
                }
                if (currentInfoBlock == "Properties") {
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
                }
                if (currentInfoBlock == "Roles") {

                    val leftRolesList =
                        gson.fromJson<ArrayList<String>>(left.roles[0], object :
                            TypeToken<ArrayList<String>>() {}.type)

                    val rightRolesList =
                        gson.fromJson<ArrayList<String>>(right.roles[0], object :
                            TypeToken<ArrayList<String>>() {}.type)

                    for (role in heroesRoles) {
                        var leftValue = "0"
                        var rightValue = "0"
                        if (role in leftRolesList) {
                            leftValue = "1"
                        }
                        if (role in rightRolesList) {
                            rightValue = "1"
                        }
                        if (leftValue != "0" || rightValue != "0") {
                            item {
                                ComparingRoleRow(
                                    leftValue,
                                    rightValue,
                                    rolesLanguageMap[role]!!
                                )
                            }
                        }
                    }
                }
                if (currentInfoBlock == "Winrates") {
                    item {
                        ComparingRow(
                            String.format("%.4f", left.turboWins / left.turboPicks.toDouble()),
                            String.format("%.4f", right.turboWins / right.turboPicks.toDouble()),
                            attrsLanguageMap["turboWinrate"]!!
                        )
                    }
                    item {
                        ComparingRow(
                            String.format("%.4f", left._1Win / left._1Pick.toDouble()),
                            String.format("%.4f", right._1Win / right._1Pick.toDouble()),
                            attrsLanguageMap["_1Winrate"]!!
                        )
                    }
                    item {
                        ComparingRow(
                            String.format("%.4f", left._2Win / left._2Pick.toDouble()),
                            String.format("%.4f", right._2Win / right._2Pick.toDouble()),
                            attrsLanguageMap["_2Winrate"]!!
                        )
                    }
                    item {
                        ComparingRow(
                            String.format("%.4f", left._3Win / left._3Pick.toDouble()),
                            String.format("%.4f", right._3Win / right._3Pick.toDouble()),
                            attrsLanguageMap["_3Winrate"]!!
                        )
                    }
                    item {
                        ComparingRow(
                            String.format("%.4f", left._4Win / left._4Pick.toDouble()),
                            String.format("%.4f", right._4Win / right._4Pick.toDouble()),
                            attrsLanguageMap["_4Winrate"]!!
                        )
                    }
                    item {
                        ComparingRow(
                            String.format("%.4f", left._5Win / left._5Pick.toDouble()),
                            String.format("%.4f", right._5Win / right._5Pick.toDouble()),
                            attrsLanguageMap["_5Winrate"]!!
                        )
                    }
                    item {
                        ComparingRow(
                            String.format("%.4f", left._6Win / left._6Pick.toDouble()),
                            String.format("%.4f", right._6Win / right._6Pick.toDouble()),
                            attrsLanguageMap["_6Winrate"]!!
                        )
                    }
                    item {
                        ComparingRow(
                            String.format("%.4f", left._7Win / left._7Pick.toDouble()),
                            String.format("%.4f", right._7Win / right._7Pick.toDouble()),
                            attrsLanguageMap["_7Winrate"]!!
                        )
                    }
                    item {
                        ComparingRow(
                            String.format("%.4f", left._8Win / left._8Pick.toDouble()),
                            String.format("%.4f", right._8Win / right._8Pick.toDouble()),
                            attrsLanguageMap["_8Winrate"]!!
                        )
                    }
                    item {
                        ComparingRow(
                            String.format("%.4f", left.proWin / left.proPick.toDouble()),
                            String.format("%.4f", right.proWin / right.proPick.toDouble()),
                            attrsLanguageMap["proWinrate"]!!
                        )
                    }
                    item {
                        ComparingRow(
                            left.proBan.toString(),
                            right.proBan.toString(),
                            attrsLanguageMap["proBan"]!!
                        )
                    }
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
                                    var favoriteFlag = favoriteHeroes.contains(hero)
                                    heroesListItemBox(onHeroClick, hero, favoriteFlag)

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
