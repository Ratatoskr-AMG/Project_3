package ru.ratatoskr.doheco.presentation.screens.favorites.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero

@Composable
fun FavoriteRowView(onHeroClick: (Hero) -> Unit, it: Hero) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .drawWithContent {
                drawContent()
            }
            .fillMaxWidth()
            .height(50.dp)

            .background(Color.Black)

    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            ) {
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
                            .width(15.dp)
                            .height(15.dp)
                    )
                }

                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onHeroClick(it)
                        },
                    painter = rememberImagePainter(it.icon),
                    contentDescription = it.name
                )
            }


        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(15.dp, 0.dp, 20.dp, 0.dp)
                    .clickable {
                        onHeroClick(it)
                    },
                fontSize = 12.sp,
                color = Color.White,
                text = it.localizedName
            )
            Image(

                modifier = Modifier
                    .width(10.dp)
                    .height(10.dp),
                painter = rememberImagePainter(
                    R.drawable.ic_back
                ),
                contentDescription = stringResource(R.string.drop_from_favorites)
            )
        }


    }
}