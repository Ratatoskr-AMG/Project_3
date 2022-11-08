package net.doheco.presentation.screens.attribute.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.doheco.domain.model.Hero
import net.doheco.presentation.screens.home.views.heroesListItemBox

@Composable
fun roleRecommendationsHeroesBlock(
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

                /*
                Box(modifier = Modifier
                    .clickable {
                        onHeroClick(hero)
                    }
                    .width(70.dp)
                    .padding(10.dp)
                    .height(35.dp)
                ) {
                    Image(
                        contentDescription = hero.name,
                        painter = loadPicture(
                            url = hero.icon,
                            placeholder = painterResource(id = R.drawable.ic_comparing_gr)
                        ),
                        modifier = Modifier
                            .width(70.dp)
                            .height(35.dp)
                    )
                }
                */
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