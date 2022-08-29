package ru.ratatoskr.doheco.presentation.screens.role.views

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.utils.rememberForeverLazyListState

@ExperimentalFoundationApi
@Composable
fun RoleListView(
    role: String,
    data: List<Any?>,
    navController: NavController,
    onHeroClick: (Hero) -> Unit
) {
    val configuration = LocalConfiguration.current
    val heroes = data.mapNotNull { it as? Hero }
    var scrollState = rememberForeverLazyListState(key = "Role_" + role)
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = consumed.y
                return Offset.Zero
            }
        }
    }
    var listColumnsCount = 4
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            listColumnsCount = 7
        }
    }
    val rolesLanguageMap: Map<String, Int> =
        mapOf(
            "Carry" to R.string.carry_role,
            "Escape" to R.string.escape_role,
            "Nuker" to R.string.nuker_role,
            "Support" to R.string.support_role,
            "Disabler" to R.string.disabler_role,
            "Jungler" to R.string.jungler_role,
            "Initiator" to R.string.initiator_role,
            "Durable" to R.string.durable_role,
            "Pusher" to R.string.pusher_role,
        )

    var roleText = if(role in rolesLanguageMap) {
        stringResource(rolesLanguageMap[role]!!)
    }else{
        role
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color.Black)
        ) {

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


                        Box(contentAlignment = Alignment.Center,

                            modifier = Modifier

                                .size(70.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color(0x880d111c), CircleShape)
                                .clickable {
                                    navController.popBackStack()
                                }
                        ) {
                            Image(

                                modifier = Modifier
                                    .width(15.dp)
                                    .height(15.dp),
                                painter = rememberImagePainter(
                                    R.drawable.ic_back
                                ),
                                contentDescription = "Back"
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(start = 15.dp)
                                .height(70.dp)
                                .width(140.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Box() {
                                Text(
                                    roleText,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    lineHeight = 20.sp
                                )
                            }

                        }

                    }
                }
            }

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
                    .fillMaxSize()
                    .background(Color.Black)
                    //.background(Color(0x55202020))
            ) {

                stickyHeader {


                }

                var listRowsCount = heroes.size / (listColumnsCount+1)
                if (heroes.size % (listColumnsCount+1) > 0) {
                    listRowsCount += 1
                }

                for (row in 0..listRowsCount - 1) {

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (column in 0..listColumnsCount) {
                                var index = column + (row * (listColumnsCount+1))
                                if (index <= heroes.size - 1) {
                                    var hero = heroes.get(index)
                                    Box(modifier = Modifier
                                        .clickable {
                                            onHeroClick(hero)
                                        }
                                        .width(70.dp)
                                        .padding(10.dp)
                                        .height(35.dp)) {

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
                                                .width(70.dp)
                                                .height(35.dp),
                                            painter = rememberImagePainter(hero.icon),
                                            contentDescription = hero.name
                                        )
                                    }

                                } else {
                                    Box(
                                        modifier = Modifier
                                            .width(70.dp)
                                            .padding(10.dp)
                                            .height(35.dp)
                                    ) {
                                    }
                                }


                            }

                        }

                    }
                }
            }
        }
    }



}