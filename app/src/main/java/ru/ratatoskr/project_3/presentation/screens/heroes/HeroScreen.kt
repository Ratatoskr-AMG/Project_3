package ru.ratatoskr.project_3.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.events.HeroEvent
import ru.ratatoskr.project_3.domain.helpers.states.HeroState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.HeroViewModel

@ExperimentalFoundationApi
@Composable
fun HeroScreen(
    id: String,
    viewModel: HeroViewModel,
    navController: NavController
) {

    val viewState = viewModel.heroState.observeAsState()

    when (val state = viewState.value) {
        is HeroState.HeroLoadedState -> {
            val isChecked = state.isFavorite
            val hero = state.hero
            HeroView(
                hero,
                navController,
                isChecked,
                onFavoriteChange = {
                    viewModel.obtainEvent(
                        HeroEvent.OnFavoriteCLick(
                            hero.id,
                            isChecked
                        )
                    )
                    /*onCheckedChange(hero.id, isChecked)*/
                },
                onRoleClick = {
                    navController.navigate(Screens.Role.route + "/" + it)
                }

            )
        }
        is HeroState.NoHeroState -> MessageView("Hero not found")
        is HeroState.LoadingHeroState -> LoadingView("Hero is loading...")
        is HeroState.ErrorHeroState -> MessageView("Hero error!")
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getHeroById(id)
    })


}


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
                                    .border(1.dp, Color(0xFF1f2430), CircleShape)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(start = 30.dp)
                                .height(70.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.padding(top = 0.dp),
                            ) {
                                Box() {
                                    Text(
                                        hero.localizedName,
                                        color = Color.White,
                                        fontSize = 20.sp,
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
                                                    fontSize = 14.sp,
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

                    Box(contentAlignment = Alignment.Center,

                        modifier = Modifier

                            .size(70.dp)
                            .background(Color.Transparent)
                            .border(1.dp, Color(0xFF0d111c), CircleShape)
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
            attributeRow(
                hero,
            "baseHealth",
            "Health",
            hero.baseHealth.toString(), navController
            )
        }
        item {
            attributeRow(
                hero,
                "baseMana",
                "Mana",
                hero.baseMana.toString(), navController
            )
        }
        item {
            attributeRow(
                hero,
                "baseHealthRegen",
                "Health regen",
                "+" + hero.baseHealthRegen.toString(),
                navController
            )
        }
        item {
            attributeRow(hero,
                "baseManaRegen",
                "Mana regen",
                "+" + hero.baseManaRegen.toString(),
                navController
            )
        }
        item { attributeRow(hero,"baseArmor", "Armor", hero.baseArmor.toString(), navController) }
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
        item { attributeRow(hero,"baseStr", "Base Strength", hero.baseStr.toString(), navController) }
        item { attributeRow(hero,"baseAgi", "Base Agility", hero.baseAgi.toString(), navController) }
        item {
            attributeRow(hero,
                "baseInt",
                "Base Intelligence",
                hero.baseInt.toString(),
                navController
            )
        }
        item {
            attributeRow(hero,
                "strGain",
                "Strength gain",
                "+" + hero.strGain.toString(),
                navController
            )
        }
        item {
            attributeRow(hero,
                "agiGain",
                "Agility gain",
                "+" + hero.agiGain.toString(),
                navController
            )
        }
        item {
            attributeRow(hero,
                "intGain",
                "Intelligence gain",
                "+" + hero.intGain.toString(),
                navController
            )
        }
        item {
            attributeRow(hero,
                "attackRange",
                "Attack range",
                hero.attackRange.toString(),
                navController
            )
        }
        if (hero.projectileSpeed > 0)
            item {
                attributeRow(hero,
                    "projectileSpeed",
                    "Projectile speed",
                    hero.projectileSpeed.toString(),
                    navController
                )
            }
        item {
            attributeRow(hero,
                "attackRate",
                "Attack rate",
                hero.attackRate.toString(),
                navController
            )
        }
        item { attributeRow(hero,"moveSpeed", "Move speed", hero.moveSpeed.toString(), navController) }
        /*item { attributeRow("cmEnabled", "Captains mode enabled", hero.cmEnabled, navController) }*/
        item { attributeRow(hero,"legs", "Legs", hero.legs.toString(), navController) }
        item {
            attributeRow(hero,
                "turboPicks",
                "Turbo picks",
                hero.turboPicks.toString(),
                navController
            )
        }
        item { attributeRow(hero,"turboWins", "Turbo wins", hero.turboWins.toString(), navController) }
        item { attributeRow(hero,"proBan", "Pro bans", hero.proBan.toString(), navController) }
        item { attributeRow(hero,"proWin", "Pro wins", hero.proWin.toString(), navController) }
        item { attributeRow(hero,"proPick", "Pro picks", hero.proPick.toString(), navController) }
        item { attributeRow(hero,"_1Pick", "Herald picks", hero._1Pick.toString(), navController) }
        item { attributeRow(hero,"_1Win", "Herald wins", hero._1Win.toString(), navController) }
        item { attributeRow(hero,"_2Pick", "Guardian picks", hero._2Pick.toString(), navController) }
        item { attributeRow(hero,"_2Win", "Guardian wins", hero._2Win.toString(), navController) }
        item { attributeRow(hero,"_3Pick", "Crusader picks", hero._3Pick.toString(), navController) }
        item { attributeRow(hero,"_3Win", "Crusader wins", hero._3Win.toString(), navController) }
        item { attributeRow(hero,"_4Pick", "Archon picks", hero._4Pick.toString(), navController) }
        item { attributeRow(hero,"_4Win", "Archon wins", hero._4Win.toString(), navController) }
        item { attributeRow(hero,"_5Pick", "Legend picks", hero._5Pick.toString(), navController) }
        item { attributeRow(hero,"_5Win", "Legend wins", hero._5Win.toString(), navController) }
        item { attributeRow(hero,"_6Pick", "Ancient picks", hero._6Pick.toString(), navController) }
        item { attributeRow(hero,"_6Win", "Ancient wins", hero._6Win.toString(), navController) }
        item { attributeRow(hero,"_7Pick", "Divine picks", hero._7Pick.toString(), navController) }
        item { attributeRow(hero,"_7Win", "Divine wins", hero._7Win.toString(), navController) }
        item { attributeRow(hero,"_8Pick", "Immortal picks", hero._8Pick.toString(), navController) }
        item { attributeRow(hero,"_8Win", "Immortal  wins", hero._8Win.toString(), navController) }

    }

}

@Composable
fun attributeRow(
    hero: Hero,
    column: String,
    name: String,
    value: String,
    navController: NavController
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0x55202020))
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
            .height(50.dp)
            .clickable {
                navController.navigate(Screens.Attr.route + "/" + column + "/" + hero.id)
            }
    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Text(
                fontSize = 12.sp,
                color = Color.White,
                text = name
            )
        }
        Box(
            modifier = Modifier
                .padding(end = 20.dp)
        ) {
            Text(
                fontSize = 12.sp,
                color = Color.White,
                text = value
            )
        }
    }
}