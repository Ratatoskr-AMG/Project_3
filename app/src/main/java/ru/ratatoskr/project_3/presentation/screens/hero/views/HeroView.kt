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
                                modifier = Modifier.padding(top = 0.dp).width(160.dp),
                            ) {
                                Box() {
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
                                                Text(
                                                    modifier = Modifier
                                                        .padding(end = 3.dp)
                                                        .clickable {
                                                            onRoleClick(role)
                                                        },
                                                    fontSize = 13.sp,
                                                    lineHeight = 18.sp,
                                                    color = Color(0xFF474b55),
                                                    text = role,
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

                            .size(70.dp)
                            .background(Color.Transparent)
                            .border(1.dp, Color(0x880d111c), CircleShape)
                            .clickable {
                                onFavoriteChange(!isChecked)
                            }
                    ) {
                        Image(

                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                            ,
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
                "Health",
                hero.baseHealth.toString(), navController
            )
        }
        item {
            HeroAttributeRowView(
                hero,
                "baseMana",
                "Mana",
                hero.baseMana.toString(), navController
            )
        }
        item {
            HeroAttributeRowView(
                hero,
                "baseHealthRegen",
                "Health regen",
                "+" + hero.baseHealthRegen.toString(),
                navController
            )
        }
        item {
            HeroAttributeRowView(hero,
                "baseManaRegen",
                "Mana regen",
                "+" + hero.baseManaRegen.toString(),
                navController
            )
        }
        item { HeroAttributeRowView(hero,"baseArmor", "Armor", hero.baseArmor.toString(), navController) }
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
        item { HeroAttributeRowView(hero,"baseStr", "Base Strength", hero.baseStr.toString(), navController) }
        item { HeroAttributeRowView(hero,"baseAgi", "Base Agility", hero.baseAgi.toString(), navController) }
        item {
            HeroAttributeRowView(hero,
                "baseInt",
                "Base Intelligence",
                hero.baseInt.toString(),
                navController
            )
        }
        item {
            HeroAttributeRowView(hero,
                "strGain",
                "Strength gain",
                "+" + hero.strGain.toString(),
                navController
            )
        }
        item {
            HeroAttributeRowView(hero,
                "agiGain",
                "Agility gain",
                "+" + hero.agiGain.toString(),
                navController
            )
        }
        item {
            HeroAttributeRowView(hero,
                "intGain",
                "Intelligence gain",
                "+" + hero.intGain.toString(),
                navController
            )
        }
        item {
            HeroAttributeRowView(hero,
                "attackRange",
                "Attack range",
                hero.attackRange.toString(),
                navController
            )
        }
        if (hero.projectileSpeed > 0)
            item {
                HeroAttributeRowView(hero,
                    "projectileSpeed",
                    "Projectile speed",
                    hero.projectileSpeed.toString(),
                    navController
                )
            }
        item {
            HeroAttributeRowView(hero,
                "attackRate",
                "Attack rate",
                hero.attackRate.toString(),
                navController
            )
        }
        item { HeroAttributeRowView(hero,"moveSpeed", "Move speed", hero.moveSpeed.toString(), navController) }
        /*item { attributeRow("cmEnabled", "Captains mode enabled", hero.cmEnabled, navController) }*/
        item { HeroAttributeRowView(hero,"legs", "Legs", hero.legs.toString(), navController) }
        item {
            HeroAttributeRowView(hero,
                "turboPicks",
                "Turbo picks",
                hero.turboPicks.toString(),
                navController
            )
        }
        item { HeroAttributeRowView(hero,"turboWins", "Turbo wins", hero.turboWins.toString(), navController) }
        item { HeroAttributeRowView(hero,"proBan", "Pro bans", hero.proBan.toString(), navController) }
        item { HeroAttributeRowView(hero,"proWin", "Pro wins", hero.proWin.toString(), navController) }
        item { HeroAttributeRowView(hero,"proPick", "Pro picks", hero.proPick.toString(), navController) }
        item { HeroAttributeRowView(hero,"_1Pick", "Herald picks", hero._1Pick.toString(), navController) }
        item { HeroAttributeRowView(hero,"_1Win", "Herald wins", hero._1Win.toString(), navController) }
        item { HeroAttributeRowView(hero,"_2Pick", "Guardian picks", hero._2Pick.toString(), navController) }
        item { HeroAttributeRowView(hero,"_2Win", "Guardian wins", hero._2Win.toString(), navController) }
        item { HeroAttributeRowView(hero,"_3Pick", "Crusader picks", hero._3Pick.toString(), navController) }
        item { HeroAttributeRowView(hero,"_3Win", "Crusader wins", hero._3Win.toString(), navController) }
        item { HeroAttributeRowView(hero,"_4Pick", "Archon picks", hero._4Pick.toString(), navController) }
        item { HeroAttributeRowView(hero,"_4Win", "Archon wins", hero._4Win.toString(), navController) }
        item { HeroAttributeRowView(hero,"_5Pick", "Legend picks", hero._5Pick.toString(), navController) }
        item { HeroAttributeRowView(hero,"_5Win", "Legend wins", hero._5Win.toString(), navController) }
        item { HeroAttributeRowView(hero,"_6Pick", "Ancient picks", hero._6Pick.toString(), navController) }
        item { HeroAttributeRowView(hero,"_6Win", "Ancient wins", hero._6Win.toString(), navController) }
        item { HeroAttributeRowView(hero,"_7Pick", "Divine picks", hero._7Pick.toString(), navController) }
        item { HeroAttributeRowView(hero,"_7Win", "Divine wins", hero._7Win.toString(), navController) }
        item { HeroAttributeRowView(hero,"_8Pick", "Immortal picks", hero._8Pick.toString(), navController) }
        item { HeroAttributeRowView(hero,"_8Win", "Immortal  wins", hero._8Win.toString(), navController) }

    }

}