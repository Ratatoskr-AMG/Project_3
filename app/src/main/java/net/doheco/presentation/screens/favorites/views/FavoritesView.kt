package net.doheco.presentation.screens.favorites.views

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import net.doheco.R
import net.doheco.domain.extensions.toArrayList
import net.doheco.domain.model.Hero
import net.doheco.domain.utils.rememberForeverLazyListState
import net.doheco.presentation.screens.favorites.FavoritesViewModel

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
fun FavoritesView(
    viewModel: FavoritesViewModel,
    heroes: List<Hero>,
    navController: NavController,
    onHeroClick: (Hero) -> Unit
) {
    val pusta: List<Hero> = emptyList()
    val mlist: MutableList<Hero> = pusta.toArrayList()
    heroes.forEach { mlist.add(it) }

    val scrollState = rememberForeverLazyListState(key = "Favorites")

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 45.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color(0x880d111c), CircleShape)
                ) {
                    Image(
                        modifier = Modifier
                            .width(15.dp)
                            .height(15.dp),
                        painter = rememberAsyncImagePainter(
                            R.drawable.ic_back
                        ),
                        contentDescription = stringResource(id = R.string.title_favorites)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .height(70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box() {
                        Text(
                            stringResource(id = R.string.title_favorites),
                            color = Color.White,
                            fontSize = 16.sp,
                            lineHeight = 20.sp
                        )
                    }

                }
            }
        }
        Divider(color = Color(0xFF0d111c))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxSize()
                    //.background(Color(0x55202020))
                    .background(Color.Black)
            ) {
                itemsIndexed(
                    items = mlist,
                    key = { _, item ->
                        item.hashCode()
                    }
                ) { _, hero ->
                    val state = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                Log.e("TOHA", "DismissedToStart")
                                mlist.remove(hero)
                                viewModel.removeFromFavorites(hero, mlist)
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color = when (state.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                //DismissDirection.EndToStart -> Color(0xFFc98000)
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(0.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(end = 10.dp)
                                ) {
                                    Text(
                                        modifier = Modifier,
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        text = stringResource(R.string.delete)
                                    )
                                }
                            }
                        },
                        dismissContent = {
                            FavoriteRowView(it = hero, onHeroClick = onHeroClick)
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                }
            }
        }
    }
}

