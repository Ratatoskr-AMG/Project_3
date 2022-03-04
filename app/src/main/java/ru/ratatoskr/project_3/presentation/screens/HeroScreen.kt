/*
::AG
Создать новый репозиторий
UseCase, который объединяет два репозитория
 */

package ru.ratatoskr.project_3.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.domain.helpers.State
import ru.ratatoskr.project_3.domain.model.Attributes
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.activity.Screens
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@ExperimentalFoundationApi
@Composable
fun HeroScreen(id: String, viewModel: HeroesListViewModel, navController: NavController) {

    val viewState = viewModel.state.observeAsState()

    when (val state = viewState.value) {
        is State.HeroLoadedState<*> -> HeroView(state.data as Hero, navController)
        is State.NoItemsState -> NoHeroesView()
        is State.LoadingState -> LoadingHeroesView()
        is State.ErrorState -> NoHeroesView()

    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getHeroById(id)
    })


}

@ExperimentalFoundationApi
@Composable
fun HeroView(hero: Hero, navController: NavController) {

    LazyColumn(modifier = Modifier.background(Color.Black)) {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = rememberImagePainter(hero.img),
                    contentDescription = hero.localizedName
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Surface(

                        color = Color.Black.copy(alpha = 0f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .align(Alignment.TopEnd)

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            horizontalArrangement = Arrangement.End
                        ) {

                            for (role in hero.roles) {
                                val gson = GsonBuilder().create()
                                val theList = gson.fromJson<ArrayList<String>>(role, object :
                                    TypeToken<ArrayList<String>>() {}.type)
                                for (role in theList) {
                                    Text(
                                        modifier = Modifier
                                            .height(30.dp)
                                            .padding(0.dp, 0.dp, 10.dp, 0.dp),
                                        fontSize = 16.sp,
                                        lineHeight = 20.sp,
                                        color = Color.White,
                                        text = role,
                                    )
                                }
                            }
                        }
                    }
                    Surface(
                        color = Color.Black.copy(alpha = 0.6f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                modifier = Modifier
                                    .height(30.dp)
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp),
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                color = Color.White,
                                text = hero.localizedName,
                            )
                        }
                    }
                }
            }

        }
        item {
            attributeRow(
                "baseHealth",
                "Health",
                hero.baseHealth.toString(), navController
            )
        }
        item {
            attributeRow(
                "baseMana",
                "Mana",
                hero.baseMana.toString(), navController
            )
        }

        item {
            attributeRow(
                "baseHealthRegen",
                "Health Regen",
                "+" + hero.baseHealthRegen.toString(),
                navController
            )
        }
        item { attributeRow("baseManaRegen", "Mana Regen", "+" + hero.baseManaRegen.toString(), navController) }
        item { attributeRow("baseArmor", "Armor", hero.baseArmor.toString(), navController) }
        item {
            attributeRow(
                "baseMr",
                "Magic Resistance",
                hero.baseMr.toString() + "%",
                navController
            )
        }

        /*
        item { attributeRow("baseAttackMin", hero.baseAttackMin.toString()) }
        item { attributeRow("baseAttackMax", hero.baseAttackMax.toString()) }
        */

        item { attributeRow("baseStr", "Base Strength", hero.baseStr.toString(), navController) }
        item { attributeRow("baseAgi", "Base Agility", hero.baseAgi.toString(), navController) }
        item {
            attributeRow(
                "baseInt",
                "Base Intelligence",
                hero.baseInt.toString(),
                navController
            )
        }
        item {
            attributeRow(
                "strGain",
                "Strength gain",
                "+" + hero.strGain.toString(),
                navController
            )
        }
        item {
            attributeRow(
                "agiGain",
                "Agility gain",
                "+" + hero.agiGain.toString(),
                navController
            )
        }
        item {
            attributeRow(
                "intGain",
                "Intelligence gain",
                "+" + hero.intGain.toString(),
                navController
            )
        }
        item {
            attributeRow(
                "attackRange",
                "Attack range",
                hero.attackRange.toString(),
                navController
            )
        }

        if (hero.projectileSpeed > 0)

            item {
                attributeRow(
                    "projectileSpeed",
                    "Projectile speed",
                    hero.projectileSpeed.toString(),
                    navController
                )
            }


        item {
            attributeRow(
                "attackRate",
                "Attack rate",
                hero.attackRate.toString(),
                navController
            )
        }
        item { attributeRow("moveSpeed", "Move speed", hero.moveSpeed.toString(), navController) }
        item { attributeRow("cmEnabled", "Captains mode enabled", hero.cmEnabled, navController) }
        item { attributeRow("legs", "Legs", hero.legs.toString(), navController) }
        item {
            attributeRow(
                "turboPicks",
                "Turbo picks",
                hero.turboPicks.toString(),
                navController
            )
        }
        item { attributeRow("turboWins", "Turbo wins", hero.turboWins.toString(), navController) }
        item { attributeRow("proBan", "Pro bans", hero.proBan.toString(), navController) }
        item { attributeRow("proWin", "Pro wins", hero.proWin.toString(), navController) }
        item { attributeRow("proPick", "Pro picks", hero.proPick.toString(), navController) }
        item { attributeRow("_1Pick", "Herald picks", hero._1Pick.toString(), navController) }
        item { attributeRow("_1Win", "Herald wins", hero._1Win.toString(), navController) }
        item { attributeRow("_2Pick", "Guardian picks", hero._2Pick.toString(), navController) }
        item { attributeRow("_2Win", "Guardian wins", hero._2Win.toString(), navController) }
        item { attributeRow("_3Pick", "Crusader picks", hero._3Pick.toString(), navController) }
        item { attributeRow("_3Win", "Crusader wins", hero._3Win.toString(), navController) }
        item { attributeRow("_4Pick", "Archon picks", hero._4Pick.toString(), navController) }
        item { attributeRow("_4Win", "Archon wins", hero._4Win.toString(), navController) }
        item { attributeRow("_5Pick", "Legend picks", hero._5Pick.toString(), navController) }
        item { attributeRow("_5Win", "Legend wins", hero._5Win.toString(), navController) }
        item { attributeRow("_6Pick", "Ancient picks", hero._6Pick.toString(), navController) }
        item { attributeRow("_6Win", "Ancient wins", hero._6Win.toString(), navController) }
        item { attributeRow("_7Pick", "Divine picks", hero._7Pick.toString(), navController) }
        item { attributeRow("_7Win", "Divine wins", hero._7Win.toString(), navController) }
        item { attributeRow("_8Pick", "Immortal picks", hero._8Pick.toString(), navController) }
        item { attributeRow("_8Win", "Immortal  wins", hero._8Win.toString(), navController) }

    }

}


@Composable
fun attributeRow(column: String, name: String, value: String, navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(10.dp)
            .background(Color.Black)
            .clickable {
                navController.navigate(Screens.Attr.route + "/" + column)
            }
    ) {
        Text(color = Color.White, text = name)
        Text(color = Color.White, text = value)
    }
}