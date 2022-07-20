package ru.ratatoskr.project_3.presentation.screens.hero.views

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.utils.rememberForeverLazyListState

@ExperimentalFoundationApi
@Composable
fun HeroView(
    hero: Hero,
    navController: NavController,
    isChecked: Boolean,
    onFavoriteChange: (Boolean) -> Unit,
    onRoleClick: (String) -> Unit
) {

    var flowRowWidth = 170.dp
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            flowRowWidth = 400.dp
        }
    }
    var scrollState = rememberForeverLazyListState(key = "Hero_" + hero.localizedName)
    val rolesLanguageMap: Map<String, Int> =
        mapOf(
            "Carry" to R.string.carry_role,
            "Escape" to R.string.escape_role,
            "Nuker" to R.string.nuker_role,
            "Support" to R.string.support_role,
            "Disabler" to R.string.disabler_role,
            "Jungler" to R.string.jungler_role,
            "Initiator" to R.string.initiator_role,
            "Durable" to R.string.durable_role,
            "Pusher" to R.string.pusher_role,
        )

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

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .background(Color.Black)
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

                        Box(
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        ) {
                            Image(
                                painter = rememberImagePainter(hero.img),
                                contentDescription = hero.localizedName,
                                contentScale = ContentScale.Crop,
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
                                    .padding(top = 0.dp)
                                    .width(200.dp),
                            ) {
                                Box(modifier = Modifier.padding(0.dp,0.dp,0.dp,0.dp)) {
                                    Text(
                                        hero.localizedName,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        lineHeight = 20.sp
                                    )
                                }
                                Box() {
                                    for (role in hero.roles) {
                                        val gson = GsonBuilder().create()
                                        val rolesList =
                                            gson.fromJson<ArrayList<String>>(role, object :
                                                TypeToken<ArrayList<String>>() {}.type)

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

                                    }
                                }

                            }
                        }
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier

                            .size(40.dp)
                            .background(Color.Transparent)
                            .border(1.dp, Color(0x880d111c), CircleShape)
                            .clickable {
                                onFavoriteChange(!isChecked)
                            }
                    ) {
                        Image(

                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            painter = if (isChecked) rememberImagePainter(R.drawable.ic_hearth_wh) else rememberImagePainter(
                                R.drawable.ic_hearth_tr
                            ),
                            contentDescription = "Is hero favorite?"
                        )
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
        item { HeroAttributeRowView(hero, "legs", "Legs", hero.legs.toString(), navController) }
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