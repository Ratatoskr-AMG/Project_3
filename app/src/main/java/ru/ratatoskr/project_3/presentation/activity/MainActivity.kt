package ru.ratatoskr.project_3.presentation.activity

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.exoplayer2.util.Log
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.*
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.ReadMe
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.states.VideoState
import ru.ratatoskr.project_3.presentation.screens.*
import ru.ratatoskr.project_3.presentation.viewmodels.*
import kotlin.math.absoluteValue

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.R)
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(color = Color.Transparent)
            ProvideWindowInsets {
                MainScreen()
            }
        }
    }

    fun stopPlayer(state: State<VideoState?>) {
        ReadMe.q2()
        when (val state = state.value) {
            is VideoState.PlayerState -> {
                state.player.stop()
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable

    fun MainScreen(
    ) {

        val navController = rememberNavController()
        val videoViewModel = hiltViewModel<VideoViewModel>()
        val videoViewState = videoViewModel.videoState.observeAsState()
        var bottomNavMenuHeight = 80.dp

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
                        listOf(Screens.Home, Screens.Favorites, Screens.Profile, Screens.Video)
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach { value ->
                        val isSelected =
                            currentDestination?.hierarchy?.any { it.route == value.route } == true
                        BottomNavigationItem(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .height(bottomNavMenuHeight)
                                .background(Color.Black)
                            ,
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
        ) { it ->
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
                        navController = navController
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
                composable(Screens.Attr.route + "/{attr}") { navBackStack ->
                    stopPlayer(videoViewState)
                    val attr = navBackStack.arguments?.getString("attr")
                    val heroesListviewModel = hiltViewModel<HeroesListViewModel>()
                    HeroesByAttributeScreen(attr!!, heroesListviewModel, navController)
                }
                composable(Screens.Favorites.route) {
                    stopPlayer(videoViewState)
                    val heroesListviewModel = hiltViewModel<HeroesListViewModel>()
                    FavoritesScreen(heroesListviewModel, navController)
                }
                composable(Screens.Profile.route) {
                    stopPlayer(videoViewState)
                    val profileViewModel = hiltViewModel<ProfileViewModel>()
                    ProfileScreen(profileViewModel)
                }
                composable(Screens.Role.route + "/{role}") { navBackStack ->
                    stopPlayer(videoViewState)
                    val role = navBackStack.arguments?.getString("role")
                    val heroesListviewModel = hiltViewModel<HeroesListViewModel>()
                    HeroesByRoleScreen(role!!, heroesListviewModel, navController)
                }
                composable(Screens.Video.route) { navBackStack ->
                    VideoScreen(videoViewModel)
                }
            }
        }


    }
}