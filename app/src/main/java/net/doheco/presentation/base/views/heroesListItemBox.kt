package net.doheco.presentation.screens.home.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import net.doheco.domain.model.Hero
import net.doheco.presentation.theme.loadPicture

@Composable
fun heroesListItemBox(onHeroClick: (Hero) -> Unit, hero: Hero, favoriteFlag: Boolean) {

    val constraintSet = getConstraints()
    Box(modifier = Modifier
        .layoutId("hero_item_box")
        .clickable {
            onHeroClick(hero)
        }
        .width(70.dp)
        .padding(10.dp)
        .height(35.dp)) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(),
            constraintSet = constraintSet
        ) {

        Image(
            contentDescription = hero.name,
            painter = loadPicture(
                url = hero.icon,
                placeholder = painterResource(id = net.doheco.R.drawable.ic_comparing_gr)
            ),
            modifier = Modifier
                .layoutId("hero_item_image")
                .width(70.dp)
                .height(35.dp)
        )
        if (favoriteFlag) {
            Image(
                contentDescription = hero.name + " is favorite",
                painter = painterResource(id = net.doheco.R.drawable.ic_yellow_star),
                modifier = Modifier
                    .layoutId("hero_item_star")
                    .width(15.dp)
                    .height(15.dp)
            )
        }
    }
    }
}


private fun getConstraints(): ConstraintSet =
    ConstraintSet {
        val hero_item_box = createRefFor("hero_item_box")
        val hero_item_image = createRefFor("hero_item_image")
        val hero_item_star = createRefFor("hero_item_star")

        constrain(hero_item_image) {
            //start.linkTo(parent.start)
            //top.linkTo(parent.top)
            //width = Dimension.fillToConstraints
        }

        constrain(hero_item_star) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
    }
