package ru.ratatoskr.project_3.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.serialization.json.Json
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.states.HeroListState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel

@ExperimentalFoundationApi
@Composable
fun AttributeScreen(
    attr: String,
    viewModel: HeroesListViewModel,
    navController: NavController
) {
    val viewState = viewModel.heroListState.observeAsState()

    when (val state = viewState.value) {
        is HeroListState.LoadedHeroListState<*> -> AttributeListView(attr, state.heroes){
            navController.navigate(Screens.Hero.route + "/" + it.id)
        }
        is HeroListState.NoHeroListState -> NoHeroesView()
        is HeroListState.LoadingHeroListState -> LoadingHeroesView()
        is HeroListState.ErrorHeroListState -> NoHeroesView()
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesByAttr(attr)
    })
}


@ExperimentalFoundationApi
@Composable
fun AttributeListView(
    attr: String,
    data: List<Any?>,
    onHeroClick: (Hero) -> Unit
) {
    val heroes = data.mapNotNull { it as? Hero }


    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    )
                )
            ),
        cells = GridCells.Fixed(count = 1),
        content = {
            heroes.forEach {

                var attrValue = ""

                when (attr) {
                    "legs" -> attrValue = it.legs.toString()
                    "baseHealth" -> attrValue = it.baseHealth.toString()
                    "baseHealthRegen" -> attrValue = "+"+it.baseHealthRegen.toString()
                    "baseMana" -> attrValue = it.baseMana.toString()
                    "baseManaRegen" -> attrValue = "+"+it.baseManaRegen.toString()
                    "baseArmor" -> attrValue = it.baseArmor.toString()
                    "baseMr" -> attrValue = it.baseMr.toString()+"%"
                    "baseStr" -> attrValue = it.baseStr.toString()
                    "baseAgi" -> attrValue = it.baseAgi.toString()
                    "baseInt" -> attrValue = it.baseInt.toString()
                    "strGain" -> attrValue = it.strGain.toString()
                    "agiGain" -> attrValue = it.agiGain.toString()
                    "intGain" -> attrValue = it.intGain.toString()
                    "attackRange" -> attrValue = it.attackRange.toString()
                    "projectileSpeed" -> attrValue = it.projectileSpeed.toString()
                    "attackRate" -> attrValue = it.attackRate.toString()
                    "moveSpeed" -> attrValue = it.moveSpeed.toString()
                    "cmEnabled" -> attrValue = it.cmEnabled
                    "turboPicks" -> attrValue = it.turboPicks.toString()
                    "turboWins" -> attrValue = it.turboWins.toString()
                    "proBan" -> attrValue = it.proBan.toString()
                    "proWin" -> attrValue = it.proWin.toString()
                    "proPick" -> attrValue = it.proPick.toString()
                    "_1Pick" -> attrValue = it._1Pick.toString()
                    "_1Win" -> attrValue = it._1Win.toString()
                    "_2Pick" -> attrValue = it._2Pick.toString()
                    "_2Win" -> attrValue = it._2Win.toString()
                    "_3Pick" -> attrValue = it._3Pick.toString()
                    "_3Win" -> attrValue = it._3Win.toString()
                    "_4Pick" -> attrValue = it._4Pick.toString()
                    "_4Win" -> attrValue = it._4Win.toString()
                    "_5Pick" -> attrValue = it._5Pick.toString()
                    "_5Win" -> attrValue = it._5Win.toString()
                    "_6Pick" -> attrValue = it._6Pick.toString()
                    "_6Win" -> attrValue = it._6Win.toString()
                    "_7Pick" -> attrValue = it._7Pick.toString()
                    "_7Win" -> attrValue = it._7Win.toString()
                    "_8Pick" -> attrValue = it._8Pick.toString()
                    "_8Win" -> attrValue = it._8Win.toString()
                }

                if (attrValue != "") {
                    item {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(1.dp)
                                .background(Color.Black)
                                .clickable {
                                    onHeroClick.invoke(it)
                                }
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(30.dp),
                                painter = rememberImagePainter(it.icon),
                                contentDescription = it.name
                            )
                            Text(modifier = Modifier.padding(10.dp), lineHeight = 50.sp, color = Color.White, text = attrValue)
                        }

                    }
                }
            }
        })

}
