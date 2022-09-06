package ru.ratatoskr.doheco.presentation.screens.hero.views

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.utils.appUtilsArrays
import ru.ratatoskr.doheco.domain.utils.rememberForeverLazyListState

@ExperimentalFoundationApi
@Composable
fun HeroView(
    hero: Hero,
    isChecked: Boolean,
    onFavoriteChange: (Boolean) -> Unit,
    onRoleClick: (String) -> Unit,
    onAttrClick: (String) -> Unit,
    navController: NavController
) {


    var flowRowWidth = 170.dp
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            flowRowWidth = 400.dp
        }
    }

    var scrollState = rememberForeverLazyListState(key = "Hero_" + hero.localizedName)

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
                        //var cAlign: Alignment = Alignment.TopCenter
                        var cAlign = appUtilsArrays.heroImgContentAlign(hero)
                        /*
                        val endAlignHeroesArray: List<String> = listOf(
                            "Death Prophet",
                            "Anti-Mage",
                            "Axe",
                            "Brewmaster",
                            "Clockwerk",
                            "Crystal Maiden",
                            "Dragon Knight",
                            "Grimstroke",
                            "Leshrac",
                            "Lifestealer",
                            "Lone Druid",
                            "Lycan",
                            "Meepo",
                            "Necrophos",
                            "Night Stalker",
                            "Pangolier",
                            "Puck",
                            "Razor",
                            "Riki",
                            "Timbersaw",
                            "Venomancer",
                            "Weaver",
                            "Zeus",
                        )
                        val startAlignHeroesArray: List<String> = listOf(
                            "Alchemist",
                            "Bristleback",
                            "Drow Ranger",
                            "Huskar",
                            "Keeper of the Light",
                            "Lina",
                            "Marci",
                            "Pudge",
                            "Shadow Shaman",
                            "Tidehunter",
                            "Troll Warlord",
                            "Ursa",
                        )
*/
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
                            /*
                            Box(
                                modifier = Modifier
                                    .padding(start = 15.dp)

                            ) {

                                    FlowRow(modifier = Modifier.width(flowRowWidth)) {
                                        for (role in rolesList) {

                                            var roleText = if (role in rolesLanguageMap) {
                                                stringResource(rolesLanguageMap[role]!!)
                                            } else {
                                                role
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
                                        }
                                    }


                            }*/

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
                        painter = if (isChecked) rememberAsyncImagePainter(R.drawable.ic_hearth_wh) else rememberAsyncImagePainter(
                            R.drawable.ic_hearth_tr
                        ),
                        contentDescription = "Is hero favorite?"
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        )
        {

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .background(Color.Black)
            ) {

                stickyHeader {

                    LazyRow(
                        //contentPadding = PaddingValues(4.dp),
                        //horizontalArrangement = Arrangement.spacedBy(4.dp),
                        //verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 0.dp, start = 0.dp, end = 0.dp)
                            .background(Color(0xFF131313))
                    ) {
                        // display items horizontally
                        items(rolesList.size) { item ->

                            var role = rolesList[item]
                            var roleText = if (role in rolesLanguageMap) {
                                stringResource(rolesLanguageMap[role]!!)
                            } else {
                                role
                            }
                            var paddingStart = if(item==0) 0.dp else 5.dp

                            Box(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom= 8.dp, start = paddingStart)
                                    //.border(1.dp, Color.White, CircleShape)
                                    .background(Color.Transparent)
                            ) {

                                Box(
                                    modifier = Modifier
                                        .padding(top = 5.dp, bottom= 5.dp, start = 20.dp, end = 20.dp)
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

                            }

                        }
                    }
                }

                item {
                    HeroAttributeRowView(
                        hero,
                        "baseHealth",
                        stringResource(attrsLanguageMap["baseHealth"]!!),
                        hero.baseHealth.toString(), navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "baseMana",
                        stringResource(attrsLanguageMap["baseMana"]!!),
                        hero.baseMana.toString(), navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "baseHealthRegen",
                        stringResource(attrsLanguageMap["baseHealthRegen"]!!),
                        "+" + hero.baseHealthRegen.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "baseManaRegen",
                        stringResource(attrsLanguageMap["baseManaRegen"]!!),
                        "+" + hero.baseManaRegen.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "baseArmor",
                        stringResource(attrsLanguageMap["baseArmor"]!!),
                        hero.baseArmor.toString(),
                        navController
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
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "baseAgi",
                        stringResource(attrsLanguageMap["baseAgi"]!!),
                        hero.baseAgi.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "baseInt",
                        stringResource(attrsLanguageMap["baseInt"]!!),
                        hero.baseInt.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "strGain",
                        stringResource(attrsLanguageMap["strGain"]!!),
                        "+" + hero.strGain.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "agiGain",
                        stringResource(attrsLanguageMap["agiGain"]!!),
                        "+" + hero.agiGain.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "intGain",
                        stringResource(attrsLanguageMap["intGain"]!!),
                        "+" + hero.intGain.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "attackRange",
                        stringResource(attrsLanguageMap["attackRange"]!!),
                        hero.attackRange.toString(),
                        navController
                    )
                }
                if (hero.projectileSpeed > 0)
                    item {
                        HeroAttributeRowView(
                            hero,
                            "projectileSpeed",
                            stringResource(attrsLanguageMap["projectileSpeed"]!!),
                            hero.projectileSpeed.toString(),
                            navController
                        )
                    }
                item {
                    HeroAttributeRowView(
                        hero,
                        "attackRate",
                        stringResource(attrsLanguageMap["attackRate"]!!),
                        hero.attackRate.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "moveSpeed",
                        stringResource(attrsLanguageMap["moveSpeed"]!!),
                        hero.moveSpeed.toString(),
                        navController
                    )
                }
                /*item { attributeRow("cmEnabled", "Captains mode enabled", hero.cmEnabled, navController) }*/
                item {
                    HeroAttributeRowView(
                        hero,
                        "legs",
                        "Legs",
                        hero.legs.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "turboPicks",
                        stringResource(attrsLanguageMap["turboPicks"]!!),
                        hero.turboPicks.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "turboWins",
                        stringResource(attrsLanguageMap["turboWins"]!!),
                        hero.turboWins.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "proBan",
                        stringResource(attrsLanguageMap["proBan"]!!),
                        hero.proBan.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "proWin",
                        stringResource(attrsLanguageMap["proWin"]!!),
                        hero.proWin.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "proPick",
                        stringResource(attrsLanguageMap["proPick"]!!),
                        hero.proPick.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_1Pick",
                        stringResource(attrsLanguageMap["_1Pick"]!!),
                        hero._1Pick.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_1Win",
                        stringResource(attrsLanguageMap["_1Win"]!!),
                        hero._1Win.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_2Pick",
                        stringResource(attrsLanguageMap["_2Pick"]!!),
                        hero._2Pick.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_2Win",
                        stringResource(attrsLanguageMap["_2Win"]!!),
                        hero._2Win.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_3Pick",
                        stringResource(attrsLanguageMap["_3Pick"]!!),
                        hero._3Pick.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_3Win",
                        stringResource(attrsLanguageMap["_3Win"]!!),
                        hero._3Win.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_4Pick",
                        stringResource(attrsLanguageMap["_4Pick"]!!),
                        hero._4Pick.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_4Win",
                        stringResource(attrsLanguageMap["_4Win"]!!),
                        hero._4Win.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_5Pick",
                        stringResource(attrsLanguageMap["_5Pick"]!!),
                        hero._5Pick.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_5Win",
                        stringResource(attrsLanguageMap["_5Win"]!!),
                        hero._5Win.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_6Pick",
                        stringResource(attrsLanguageMap["_6Pick"]!!),
                        hero._6Pick.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_6Win",
                        stringResource(attrsLanguageMap["_6Win"]!!),
                        hero._6Win.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_7Pick",
                        stringResource(attrsLanguageMap["_7Pick"]!!),
                        hero._7Pick.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_7Win",
                        stringResource(attrsLanguageMap["_7Win"]!!),
                        hero._7Win.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_8Pick",
                        stringResource(attrsLanguageMap["_8Pick"]!!),
                        hero._8Pick.toString(),
                        navController
                    )
                }
                item {
                    HeroAttributeRowView(
                        hero,
                        "_8Win",
                        stringResource(attrsLanguageMap["_8Win"]!!),
                        hero._8Win.toString(),
                        navController
                    )
                }

            }

        }
    }


}