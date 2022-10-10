package ru.ratatoskr.doheco.presentation.screens.hero.views

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.utils.AttributeMaximum
import ru.ratatoskr.doheco.domain.utils.appUtilsArrays
import ru.ratatoskr.doheco.domain.utils.rememberForeverLazyListState
import ru.ratatoskr.doheco.presentation.base.infoBlocks
import ru.ratatoskr.doheco.presentation.base.views.pageTitleBlock
import ru.ratatoskr.doheco.presentation.screens.hero.HeroViewModel

@ExperimentalFoundationApi
@Composable
fun HeroView(
    viewModel: HeroViewModel,
    hero: Hero,
    isChecked: Boolean,
    currentInfoBlock: String,
    currentAttrsMax: List<AttributeMaximum>,
    onFavoriteChange: (Boolean) -> Unit,
    onRoleClick: (String) -> Unit,
    onAttrClick: (String) -> Unit,
    onHeroInfoBlockSelect: (String) -> Unit,
    navController: NavController,
    scrollState:LazyListState
) {

    //var scrollState = rememberForeverLazyListState(key = "Hero_" + hero.localizedName)
    val rolesLanguageMap = appUtilsArrays.rolesLanguageMap()
    val attrsLanguageMap = appUtilsArrays.attrsLanguageMap()
    val gson = GsonBuilder().create()
    val rolesList =
        gson.fromJson<ArrayList<String>>(hero.roles[0], object :
            TypeToken<ArrayList<String>>() {}.type)

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

                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(70.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    ) {

                        var cScale: ContentScale = ContentScale.Crop
                        var cAlign = appUtilsArrays.heroImgContentAlign(hero)
                        val startAlignHeroesList = appUtilsArrays.startAlignHeroesArray()
                        val endAlignHeroesList = appUtilsArrays.endAlignHeroesArray()
                        if (hero.localizedName in endAlignHeroesList) {
                            cAlign = Alignment.TopEnd
                        }
                        if (hero.localizedName in startAlignHeroesList) {
                            cAlign = Alignment.TopStart
                        }

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
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                        }

                        Image(
                            painter = rememberAsyncImagePainter(hero.img),
                            contentDescription = hero.localizedName,
                            alignment = cAlign,
                            contentScale = cScale,
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color(0x880d111c), CircleShape)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .height(70.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 0.dp),
                        ) {
                            Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)) {
                                Text(
                                    hero.localizedName,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color.Transparent)
                        .clickable {
                            onFavoriteChange(!isChecked)
                        }
                        .clip(CircleShape)
                        .border(1.dp, Color(0x880d111c), CircleShape)
                ) {
                    Image(

                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        painter = if (isChecked) rememberAsyncImagePainter(R.drawable.ic_star_wh) else rememberAsyncImagePainter(
                            R.drawable.ic_star_tr
                        ),
                        contentDescription = "Is hero favorite?"
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        )
        {

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .background(Color.Black)

            ) {

                val constraintSet: ConstraintSet =
                    ConstraintSet {
                        /*
                           val hero_roles_lazy_row = createRefFor("hero_roles_lazy_row")
                           val hero_roles_shadow_left = createRefFor("hero_roles_shadow_left")
                           val hero_roles_shadow_right = createRefFor("hero_roles_shadow_right")

                           constrain(hero_roles_shadow_left) {
                               end.linkTo(parent.end)
                               top.linkTo(parent.top)
                           }
                           constrain(hero_roles_shadow_right) {
                               start.linkTo(parent.start)
                               top.linkTo(parent.top)
                           }
                        */
                    }

                stickyHeader {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize(),
                        constraintSet = constraintSet
                    ) {

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
                                    var textContent = stringResource(id = R.string.picks)
                                    if (currentInfoBlock == "Wins") {
                                        textContent = stringResource(id = R.string.wins)
                                    }
                                    if (currentInfoBlock == "Properties") {
                                        textContent = stringResource(id = R.string.properties)
                                    }
                                    if (currentInfoBlock == "Roles") {
                                        textContent = stringResource(id = R.string.roles)
                                    }
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
                                            text = infoBlockName
                                        )
                                    }

                                }
                            }
                        }
                        /* Shadows
                        Box(
                            modifier = Modifier
                                .layoutId("hero_roles_shadow_left")
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0x00000000),
                                            Color(0xFF000000),
                                            Color(0xFF000000)
                                        )
                                    )
                                )
                                .width(30.dp)
                                .height(38.dp)

                        )

                        Box(
                            modifier = Modifier
                                .layoutId("hero_roles_shadow_right")
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF000000),
                                            Color(0xFF000000),
                                            Color(0x00000000)
                                        )
                                    )
                                )
                                .width(30.dp)
                                .height(38.dp)

                        )

                        */
                    }
                }

                if (currentInfoBlock == "Picks") {
                    item {
                        HeroAttributeRowView(
                            hero,
                            "turboPicks",
                            stringResource(attrsLanguageMap["turboPicks"]!!),
                            hero.turboPicks.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "turboPicks" }[0].getvalue()
                                .toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_1Pick",
                            stringResource(attrsLanguageMap["_1Pick"]!!),
                            hero._1Pick.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_1Pick" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_2Pick",
                            stringResource(attrsLanguageMap["_2Pick"]!!),
                            hero._2Pick.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_2Pick" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_3Pick",
                            stringResource(attrsLanguageMap["_3Pick"]!!),
                            hero._3Pick.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_3Pick" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_4Pick",
                            stringResource(attrsLanguageMap["_4Pick"]!!),
                            hero._4Pick.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_4Pick" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_5Pick",
                            stringResource(attrsLanguageMap["_5Pick"]!!),
                            hero._5Pick.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_5Pick" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_6Pick",
                            stringResource(attrsLanguageMap["_6Pick"]!!),
                            hero._6Pick.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_6Pick" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_7Pick",
                            stringResource(attrsLanguageMap["_7Pick"]!!),
                            hero._7Pick.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_7Pick" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_8Pick",
                            stringResource(attrsLanguageMap["_8Pick"]!!),
                            hero._8Pick.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_8Pick" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "proPick",
                            stringResource(attrsLanguageMap["proPick"]!!),
                            hero.proPick.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "proPick" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "proBan",
                            stringResource(attrsLanguageMap["proBan"]!!),
                            hero.proBan.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "proBan" }[0].getvalue().toFloat()
                        )
                    }
                }
                if (currentInfoBlock == "Wins") {
                    item {
                        HeroAttributeRowView(
                            hero,
                            "turboWins",
                            stringResource(attrsLanguageMap["turboWins"]!!),
                            hero.turboWins.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "turboWins" }[0].getvalue()
                                .toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_1Win",
                            stringResource(attrsLanguageMap["_1Win"]!!),
                            hero._1Win.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_1Win" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_2Win",
                            stringResource(attrsLanguageMap["_2Win"]!!),
                            hero._2Win.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_2Win" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_3Win",
                            stringResource(attrsLanguageMap["_3Win"]!!),
                            hero._3Win.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_3Win" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_4Win",
                            stringResource(attrsLanguageMap["_4Win"]!!),
                            hero._4Win.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_4Win" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_5Win",
                            stringResource(attrsLanguageMap["_5Win"]!!),
                            hero._5Win.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_5Win" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_6Win",
                            stringResource(attrsLanguageMap["_6Win"]!!),
                            hero._6Win.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_6Win" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_7Win",
                            stringResource(attrsLanguageMap["_7Win"]!!),
                            hero._7Win.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_7Win" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "_8Win",
                            stringResource(attrsLanguageMap["_8Win"]!!),
                            hero._8Win.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "_8Win" }[0].getvalue().toFloat()

                        )
                    }

                    item {
                        HeroAttributeRowView(
                            hero,
                            "proWin",
                            stringResource(attrsLanguageMap["proWin"]!!),
                            hero.proWin.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "proWin" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "proBan",
                            stringResource(attrsLanguageMap["proBan"]!!),
                            hero.proBan.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "proBan" }[0].getvalue().toFloat()

                        )
                    }
                }
                if (currentInfoBlock == "Properties") {
                    item {
                        HeroAttributeRowView(
                            hero,
                            "baseHealth",
                            stringResource(attrsLanguageMap["baseHealth"]!!),
                            hero.baseHealth.toString(), navController,
                            currentAttrsMax.filter { it.name == "baseHealth" }[0].getvalue()
                                .toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "baseMana",
                            stringResource(attrsLanguageMap["baseMana"]!!),
                            hero.baseMana.toString(), navController,
                            currentAttrsMax.filter { it.name == "baseMana" }[0].getvalue().toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "baseHealthRegen",
                            stringResource(attrsLanguageMap["baseHealthRegen"]!!),
                            "+" + hero.baseHealthRegen.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "baseHealthRegen" }[0].getvalue()
                                .toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "baseManaRegen",
                            stringResource(attrsLanguageMap["baseManaRegen"]!!),
                            "+" + hero.baseManaRegen.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "baseManaRegen" }[0].getvalue()
                                .toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "baseArmor",
                            stringResource(attrsLanguageMap["baseArmor"]!!),
                            hero.baseArmor.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "baseArmor" }[0].getvalue()
                                .toFloat()

                        )
                    }
                    /*
                    item {
                        attributeRow(
                            "baseMr",
                            "Magic Resistance",
                            hero.baseMr.toString() + "%",
                            navController
                        )
                    }
                    item { attributeRow("baseAttackMin", hero.baseAttackMin.toString()) }
                    item { attributeRow("baseAttackMax", hero.baseAttackMax.toString()) }
                    */
                    item {
                        HeroAttributeRowView(
                            hero,
                            "baseStr",
                            stringResource(attrsLanguageMap["baseStr"]!!),
                            hero.baseStr.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "baseStr" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "baseAgi",
                            stringResource(attrsLanguageMap["baseAgi"]!!),
                            hero.baseAgi.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "baseAgi" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "baseInt",
                            stringResource(attrsLanguageMap["baseInt"]!!),
                            hero.baseInt.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "baseInt" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "strGain",
                            stringResource(attrsLanguageMap["strGain"]!!),
                            "+" + hero.strGain.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "strGain" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "agiGain",
                            stringResource(attrsLanguageMap["agiGain"]!!),
                            "+" + hero.agiGain.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "agiGain" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "intGain",
                            stringResource(attrsLanguageMap["intGain"]!!),
                            "+" + hero.intGain.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "intGain" }[0].getvalue().toFloat()
                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "attackRange",
                            stringResource(attrsLanguageMap["attackRange"]!!),
                            hero.attackRange.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "attackRange" }[0].getvalue()
                                .toFloat()

                        )
                    }
                    if (hero.projectileSpeed > 0)
                        item {
                            HeroAttributeRowView(
                                hero,
                                "projectileSpeed",
                                stringResource(attrsLanguageMap["projectileSpeed"]!!),
                                hero.projectileSpeed.toString(),
                                navController,
                                currentAttrsMax.filter { it.name == "projectileSpeed" }[0].getvalue()
                                    .toFloat()

                            )
                        }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "attackRate",
                            stringResource(attrsLanguageMap["attackRate"]!!),
                            hero.attackRate.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "attackRate" }[0].getvalue()
                                .toFloat()

                        )
                    }
                    item {
                        HeroAttributeRowView(
                            hero,
                            "moveSpeed",
                            stringResource(attrsLanguageMap["moveSpeed"]!!),
                            hero.moveSpeed.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "moveSpeed" }[0].getvalue()
                                .toFloat()

                        )
                    }
                    /*item { attributeRow("cmEnabled", "Captains mode enabled", hero.cmEnabled, navController) }*/
                    /*item {
                        HeroAttributeRowView(
                            hero,
                            "legs",
                            "Legs",
                            hero.legs.toString(),
                            navController,
                            currentAttrsMax.filter { it.name == "Legs" }[0].getvalue().toFloat()
                        )
                    }*/
                }
                if (currentInfoBlock == "Roles") {
                    for (role in rolesList) {
                        item {
                            var roleText = if (role in rolesLanguageMap) {
                                stringResource(rolesLanguageMap[role]!!)
                            } else {
                                role
                            }
                            HeroRoleRowView(
                                navController,
                                roleText,
                                role
                            )
                            /*
                            Box(
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .clickable {
                                        onRoleClick(role)
                                    }
                            ) {
                                Text(
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    text = roleText
                                )
                            }

                            Text(
                                modifier = Modifier
                                    .padding(end = 3.dp)
                                    .clickable {
                                        onRoleClick(role)
                                    },
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                                color = Color(0xFF474b55),
                                text = roleText,
                            )
                            */

                        }
                    }
                }

            }
        }

    }
}
