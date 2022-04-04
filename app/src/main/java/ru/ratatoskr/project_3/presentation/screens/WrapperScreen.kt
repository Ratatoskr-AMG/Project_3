package ru.ratatoskr.project_3.presentation.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.ReadMe
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.states.ProfileState
import ru.ratatoskr.project_3.domain.helpers.states.VideoState
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.viewmodels.HeroViewModel
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel
import ru.ratatoskr.project_3.presentation.viewmodels.ProfileViewModel
import ru.ratatoskr.project_3.presentation.viewmodels.VideoViewModel
import java.util.prefs.Preferences

@ExperimentalFoundationApi
@Composable
fun WrapperScreen(
    opendotaUpdatePreferences: SharedPreferences
) {

    val navController = rememberNavController()
    val videoViewModel = hiltViewModel<VideoViewModel>()
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val videoViewState = videoViewModel.videoState.observeAsState()
    val profileViewState = profileViewModel.profileState.observeAsState()
    var bottomNavMenuHeight = 80.dp

    fun stopPlayer(state: State<VideoState?>) {
        ReadMe.q2()
        when (val state = state.value) {
            is VideoState.PlayerState -> {
                state.player.stop()
            }
        }
    }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .drawWithContent {
                        drawContent()
                        clipRect { // Not needed if you do not care about painting half stroke outside
                            val strokeWidth = Stroke.DefaultMiter
                            val y = size.height / size.height // strokeWidth
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
                    .height(bottomNavMenuHeight),
                backgroundColor = Color(0xFF000000)
            ) {
                val items =
                    listOf(Screens.Home, Screens.Favorites, Screens.Profile)
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { value ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.route == value.route } == true
                    BottomNavigationItem(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .height(bottomNavMenuHeight)
                            .background(Color.Black),
                        selected = isSelected,
                        onClick = {
                            navController.navigate(value.route)
                        }, icon = {
                            Image(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                                    .align(Alignment.CenterVertically),
                                painter = if (isSelected) rememberImagePainter(value.icon_wh) else rememberImagePainter(
                                    value.icon_tr
                                ),
                                contentDescription = value.description.toString(),
                            )
                        })
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screens.Home.route) {
                stopPlayer(videoViewState)
                val heroesListviewModel = hiltViewModel<HeroesListViewModel>()
                HeroesListScreen(
                    viewModel = heroesListviewModel,
                    navController = navController,
                    opendotaUpdatePreferences
                )
            }
            composable(Screens.Hero.route + "/{id}") { navBackStack ->
                stopPlayer(videoViewState)
                val id = navBackStack.arguments?.getString("id").toString()
                val heroViewModel = hiltViewModel<HeroViewModel>()
                HeroScreen(
                    id,
                    heroViewModel,
                    navController
                )
            }
            composable(Screens.Role.route + "/{role}") { navBackStack ->
                stopPlayer(videoViewState)
                val role = navBackStack.arguments?.getString("role")
                val heroesListviewModel = hiltViewModel<HeroesListViewModel>()
                HeroesByRoleScreen(role!!, heroesListviewModel, navController)
            }
            composable(Screens.Attr.route + "/{attr}/{id}") { navBackStack ->
                stopPlayer(videoViewState)
                val attr = navBackStack.arguments?.getString("attr")
                val id = navBackStack.arguments?.getString("id")
                val heroesListviewModel = hiltViewModel<HeroesListViewModel>()
                HeroesByAttributeScreen(attr!!, id!!, heroesListviewModel, navController)
            }
            composable(Screens.Favorites.route) {
                stopPlayer(videoViewState)
                val heroesListviewModel = hiltViewModel<HeroesListViewModel>()
                FavoritesScreen(heroesListviewModel, navController)
            }

            composable(Screens.Profile.route) {
                stopPlayer(videoViewState)
                val profileViewModel = hiltViewModel<ProfileViewModel>()
                ProfileScreen(navController, profileViewState, profileViewModel,opendotaUpdatePreferences)
            }
            composable(Screens.Steam.route) {
                SteamLoginScreen(navController, profileViewState, profileViewModel)
            }

            composable(Screens.Video.route) { navBackStack ->
                VideoScreen(videoViewModel)
            }
        }
    }

}