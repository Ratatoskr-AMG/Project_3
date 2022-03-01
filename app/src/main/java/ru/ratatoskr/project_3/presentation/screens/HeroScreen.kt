package ru.ratatoskr.project_3.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import ru.ratatoskr.project_3.domain.helpers.State
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@ExperimentalFoundationApi
@Composable
fun HeroScreen(id: String, viewModel: HeroesListViewModel, navController: NavController) {

    val viewState = viewModel.state.observeAsState()

    when (val state = viewState.value) {
        is State.HeroLoadedState<*> -> HeroView(state.data as Hero)
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
fun HeroView(hero: Hero) {

    val strHealhMultiplier = 20;
    val strHealhRegenMultiplier = 0.1;
    val intManaMultiplier = 12;
    val intManaRegenMultiplier = 0.05;
    val agiArmorMultiplier = 0.167;

    /*
    Создать новый репозиторий
    UseCase, который объединяет два репозитория
     */

    val mathContext = MathContext(200, RoundingMode.HALF_DOWN)
    var healthRegen =
        BigDecimal(((hero.baseStr) * strHealhRegenMultiplier) + hero.baseHealthRegen, mathContext)
    var manaRegen =
        BigDecimal(((hero.baseInt) * intManaRegenMultiplier) + hero.baseManaRegen, mathContext)
    var armor =
        BigDecimal(((hero.baseAgi) * agiArmorMultiplier) + hero.baseArmor, mathContext)

    healthRegen = healthRegen.setScale(1, BigDecimal.ROUND_HALF_DOWN)
    manaRegen = manaRegen.setScale(1, BigDecimal.ROUND_HALF_DOWN)
    armor = armor.setScale(1, BigDecimal.ROUND_HALF_DOWN)

    //var basehealthRegen = (hero.baseHealthRegen+((hero.baseStr)*strHealhRegenMultiplier)).roundToLong()

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
                "Health",
                (hero.baseHealth + hero.baseStr * strHealhMultiplier).toString()
            )
        }
        item {
            attributeRow(
                "Mana",
                (hero.baseMana + hero.baseInt * intManaMultiplier).toString()
            )
        }
        //item { attributeRow("Health Regen", "+"+healthRegen.toString()) } не всегда совпадает с оригиналом
        //item { attributeRow("Mana Regen", "+"+manaRegen.toString()) } не всегда совпадает с оригиналом
        item { attributeRow("Magic Resistance", hero.baseMr.toString()+"%")}
        item { attributeRow("Armor", armor.toString()) }
        /*
        item { attributeRow("baseAttackMin", hero.baseAttackMin.toString()) }
        item { attributeRow("baseAttackMax", hero.baseAttackMax.toString()) }
        */
        item { attributeRow("Base Strength", hero.baseStr.toString()) }
        item { attributeRow("Base Agility", hero.baseAgi.toString()) }
        item { attributeRow("Base Intelligence", hero.baseInt.toString()) }
        item { attributeRow("Strength gain", "+"+hero.strGain.toString()) }
        item { attributeRow("Agility gain", "+"+hero.agiGain.toString()) }
        item { attributeRow("Intelligence gain", "+"+hero.intGain.toString()) }
        item { attributeRow("Attack range", hero.attackRange.toString()) }
        if(hero.projectileSpeed>0)
        item { attributeRow("Projectile speed", hero.projectileSpeed.toString()) }
        item { attributeRow("Attack rate", hero.attackRate.toString()) }
        item { attributeRow("Move speed", hero.moveSpeed.toString()) }
        item { attributeRow("Captains mode enabled", hero.cmEnabled.toString()) }
        item { attributeRow("Legs", hero.legs.toString()) }
        item { attributeRow("Turbo picks", hero.turboPicks.toString()) }
        item { attributeRow("Turbo wins", hero.turboWins.toString()) }
        item { attributeRow("Pro bans", hero.proBan.toString()) }
        item { attributeRow("Pro wins", hero.proWin.toString()) }
        item { attributeRow("Pro picks", hero.proPick.toString()) }
        item { attributeRow("Herald picks", hero._1Pick.toString()) }
        item { attributeRow("Herald wins", hero._1Win.toString()) }
        item { attributeRow("Guardian picks", hero._2Pick.toString()) }
        item { attributeRow("Guardian wins", hero._2Win.toString()) }
        item { attributeRow("Crusader picks", hero._3Pick.toString()) }
        item { attributeRow("Crusader wins", hero._3Win.toString()) }
        item { attributeRow("Archon picks", hero._4Pick.toString()) }
        item { attributeRow("Archon wins", hero._4Win.toString()) }
        item { attributeRow("Legend picks", hero._5Pick.toString()) }
        item { attributeRow("Legend wins", hero._5Win.toString()) }
        item { attributeRow("Ancient picks", hero._6Pick.toString()) }
        item { attributeRow("Ancient wins", hero._6Win.toString()) }
        item { attributeRow("Divine picks", hero._7Pick.toString()) }
        item { attributeRow("Divine wins", hero._7Win.toString()) }
        item { attributeRow("Immortal picks", hero._8Pick.toString()) }
        item { attributeRow("Immortal  wins", hero._8Win.toString()) }

    }

}


@Composable
fun attributeRow(name: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(10.dp)
            .background(Color.Black)
    ) {
        Text(color = Color.White, text = name)
        Text(color = Color.White, text = value)
    }
}