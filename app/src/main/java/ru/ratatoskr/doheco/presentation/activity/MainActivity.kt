package ru.ratatoskr.doheco.presentation.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ru.ratatoskr.doheco.presentation.base.Screens
import ru.ratatoskr.doheco.presentation.screens.*
import ru.ratatoskr.doheco.presentation.screens.attribute.AttributeViewModel
import ru.ratatoskr.doheco.presentation.screens.comparing.ComparingScreen
import ru.ratatoskr.doheco.presentation.screens.comparing.ComparingViewModel
import ru.ratatoskr.doheco.presentation.screens.favorites.FavoritesViewModel
import ru.ratatoskr.doheco.presentation.screens.profile.ProfileViewModel
import ru.ratatoskr.doheco.presentation.screens.steam.SteamViewModel
import ru.ratatoskr.doheco.presentation.screens.tiers.TiersViewModel
import ru.ratatoskr.doheco.presentation.screens.home.HomeScreen
import ru.ratatoskr.doheco.presentation.screens.hero.HeroViewModel
import ru.ratatoskr.doheco.presentation.screens.role.RoleViewModel
import ru.ratatoskr.doheco.presentation.screens.tiers.TiersScreen
import ru.ratatoskr.doheco.presentation.screens.home.HomeViewModel
import ru.ratatoskr.doheco.presentation.screens.recommendations.RecommendationsScreen
import ru.ratatoskr.doheco.presentation.screens.recommendations.RecommendationsViewModel
import ru.ratatoskr.doheco.presentation.screens.video.VideoViewModel

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {

    val APP_SHARED_PREFERENCES_NAME = "app_preferences"

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val appSharedPreferences = this.getSharedPreferences(
            APP_SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

        val imageLoader = ImageLoader.Builder(this)
            .crossfade(true)
            .build()

        setContent {
            rememberSystemUiController().setStatusBarColor(color = Color.Transparent)
            ProvideWindowInsets {
                val navController = rememberNavController()
                var bottomNavMenuHeight = 80.dp
                Scaffold(
                    modifier = Modifier.navigationBarsPadding().background(Color.Black),
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

                            val bottomMenuItems =
                                listOf(
                                    Screens.Home,
                                    Screens.Favorites,
                                    Screens.Comparing,
                                    Screens.Recommendations,
                                    Screens.Profile
                                )

                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            bottomMenuItems.forEach { value ->
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
                                            painter = if (isSelected) rememberAsyncImagePainter(
                                                value.icon_wh
                                            ) else rememberAsyncImagePainter(
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

                            val heroesListviewModel = hiltViewModel<HomeViewModel>()

                            HomeScreen(
                                viewModel = heroesListviewModel,
                                navController = navController,
                            )

                        }
                        composable(Screens.Hero.route + "/{id}") { navBackStack ->
                            val id = navBackStack.arguments?.getString("id").toString()
                            val heroViewModel = hiltViewModel<HeroViewModel>()
                            HeroScreen(
                                id,
                                heroViewModel,
                                navController
                            )
                        }
                        composable(Screens.Role.route + "/{role}") { navBackStack ->
                            val role = navBackStack.arguments?.getString("role")
                            val roleViewModel = hiltViewModel<RoleViewModel>()
                            RoleScreen(role!!, roleViewModel, navController)
                        }
                        composable(Screens.Attr.route + "/{attr}/{id}") { navBackStack ->
                            val attr = navBackStack.arguments?.getString("attr")
                            val id = navBackStack.arguments?.getString("id")
                            val attributeListviewModel = hiltViewModel<AttributeViewModel>()
                            AttributeScreen(attr!!, id!!, attributeListviewModel, navController)
                        }
                        composable(Screens.Favorites.route) {
                            val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
                            FavoritesScreen(favoritesViewModel, navController)
                        }
                        composable(Screens.Profile.route) {
                            val profileViewModel = hiltViewModel<ProfileViewModel>()
                            ProfileScreen(navController, profileViewModel, appSharedPreferences)
                        }
                        composable(Screens.Steam.route) {
                            val steamViewModel = hiltViewModel<SteamViewModel>()
                            SteamScreen(navController, steamViewModel)
                        }
                        composable(Screens.Tier.route) {
                            val tiersViewModel = hiltViewModel<TiersViewModel>()
                            TiersScreen(navController, tiersViewModel, appSharedPreferences)
                        }
                        composable(Screens.Video.route) {
                            val videoViewModel = hiltViewModel<VideoViewModel>()
                            VideoScreen(videoViewModel)
                        }
                        composable(Screens.Comparing.route) {
                            val comparingViewModel = hiltViewModel<ComparingViewModel>()
                            ComparingScreen(comparingViewModel)
                        }
                        composable(Screens.Recommendations.route) { navBackStack->
                            val recommendationsViewModel = hiltViewModel<RecommendationsViewModel>()
                            RecommendationsScreen(navController,recommendationsViewModel)
                        }
                    }
                }

            }
        }
    }

}