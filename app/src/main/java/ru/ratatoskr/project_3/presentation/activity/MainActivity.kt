package ru.ratatoskr.project_3.presentation.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.events.HeroEvent
import ru.ratatoskr.project_3.domain.helpers.events.ProfileEvent
import ru.ratatoskr.project_3.presentation.screens.AttributeScreen
import ru.ratatoskr.project_3.presentation.screens.FavoritesScreen
import ru.ratatoskr.project_3.presentation.screens.HeroScreen
import ru.ratatoskr.project_3.presentation.screens.ProfileScreen
import ru.ratatoskr.project_3.presentation.screens.HeroesListScreen
import ru.ratatoskr.project_3.presentation.viewmodels.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MainScreen(
) {

    val navController = rememberNavController()
    val heroesListviewModel = hiltViewModel<HeroesListViewModel>()
    val heroViewModel = hiltViewModel<HeroViewModel>()
    val profileViewModel = hiltViewModel<ProfileViewModel>()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .background(Color.Gray)
                    .height(35.dp),
                backgroundColor = Color.White
            ) {
                val items = listOf(Screens.Home, Screens.Favorites, Screens.Profile)
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { value ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.route == value.route } == true

                    BottomNavigationItem(selected = isSelected, onClick = {
                        navController.navigate(value.route)
                    }, icon = {
                    }, label = {
                        Text(
                            lineHeight = 20.sp,
                            text = stringResource(id = value.stringId),
                            color = if (isSelected) Color.Gray else Color.Black
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
                HeroesListScreen(
                    viewModel = heroesListviewModel,
                    navController = navController
                )
            }
            composable(Screens.Hero.route + "/{id}") { navBackStack ->
                val id = navBackStack.arguments?.getString("id").toString()
                HeroScreen(
                    id,
                    heroViewModel,
                    navController)
            }
            composable(Screens.Attr.route + "/{attr}") { navBackStack ->
                val attr = navBackStack.arguments?.getString("attr")
                AttributeScreen(attr!!, heroesListviewModel, navController)
            }
            composable(Screens.Favorites.route) {
                FavoritesScreen(heroesListviewModel, navController)
            }
            composable(Screens.Profile.route) {

                ProfileScreen(profileViewModel)

            }
        }

    }


}