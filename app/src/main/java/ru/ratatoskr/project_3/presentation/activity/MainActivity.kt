package ru.ratatoskr.project_3.presentation.activity
/*
Модуль клиент+
Карточка героя
Размер картинок
Карточка атрибута
Теория(?)
Карьера программиста крэкинг зэ пот
Java Concurrency in Practiсe
 */
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.composable.MyComposable
import ru.ratatoskr.project_3.presentation.screens.HeroesListScreen
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel

sealed class Routes(val route: String) {
    object HeroesList : Routes("HeroesList")
    object Hero : Routes("Hero")
    object WaitScreen : Routes("WaitScreen")
}
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //private val viewModel = HeroesListViewModel(repository = HeroesRepoImpl())
    //var myComposable = MyComposable()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "main") {
                composable("main") { MainScreen(navController) }
            }
        }
    }
}

sealed class Screens(val route: String, val stringId: Int) {
    object Home: Screens("home", R.string.title_home)
    object Dashboard: Screens("dashboard", R.string.title_home)
    object Notifications: Screens("notifications", R.string.title_home)
}

@ExperimentalFoundationApi
@Composable
fun MainScreen(parentNavController: NavController) {
    val navController = rememberNavController()
    val items = listOf(Screens.Home, Screens.Dashboard, Screens.Notifications)

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { value ->
                    val isSelected = currentDestination?.hierarchy?.any { it.route == value.route } == true

                    BottomNavigationItem(selected = isSelected, onClick = {
                        navController.navigate(value.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }, icon = {

                    }, label = {
                        Text(stringResource(id = value.stringId), color = if (isSelected) Color.Black else Color.LightGray)
                    })
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screens.Home.route, modifier = Modifier.padding(it)) {
            composable(Screens.Home.route) {
                val viewModel = hiltViewModel<HeroesListViewModel>()
                HeroesListScreen(viewModel = viewModel, navController = navController)
            }

            composable(Screens.Dashboard.route) { Text("Dashboard") }
            composable(Screens.Notifications.route) { Text("Notifications") }
        }
    }
}