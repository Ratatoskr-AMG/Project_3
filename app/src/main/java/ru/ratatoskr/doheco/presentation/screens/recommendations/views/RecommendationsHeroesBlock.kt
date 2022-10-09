package ru.ratatoskr.doheco.presentation.screens.recommendations.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.presentation.screens.home.views.heroesListItemBox
import ru.ratatoskr.doheco.presentation.theme.loadPicture

@Composable
fun recommendationsHeroesBlock(
    heroes: List<Hero>,
    favoriteHeroes: List<Hero>,
    listColumnsCount: Int,
    row: Int,
    onHeroClick: (Hero) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (column in 0..listColumnsCount) {
            var index = column + (row * (listColumnsCount + 1))
            if (index <= heroes.size - 1) {
                var hero = heroes.get(index)
                var favoriteFlag = favoriteHeroes.contains(hero)
                heroesListItemBox(onHeroClick,hero,favoriteFlag)
            } else {
                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .padding(10.dp)
                        .height(35.dp)
                )
            }
        }


    }
}