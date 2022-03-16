package ru.ratatoskr.project_3.presentation.activity

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ratatoskr.project_3.R
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
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main") {
                composable("main") { MainScreen() }
            }
        }
    }
}

sealed class Screens(val route: String, val stringId: Int) {
    object Home : Screens("home", R.string.title_home)
    object Hero : Screens("hero", R.string.title_hero)
    object Attr : Screens("attr", R.string.title_hero)
    object Favorites : Screens("favorites", R.string.title_favorites)
    object Profile : Screens("profile", R.string.title_profile)
}

@ExperimentalFoundationApi
@Composable
fun MainScreen(
) {
    val navController = rememberNavController()
    val items = listOf(Screens.Home, Screens.Favorites, Screens.Profile)
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
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { value ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.route == value.route } == true

                    BottomNavigationItem(selected = isSelected, onClick = {

                        navController.navigate(value.route) {

                            /*
                            Всплывающее окно с заданным пунктом назначения перед навигацией.
                            Это удаляет все несоответствующие пункты назначения из заднего стека
                            до тех пор, пока это место назначения не будет найдено.
                             */
                            //popUpTo(navController.graph.findStartDestination().id) {

                            /*
                                Следует ли сохранять обратный стек и состояние всех пунктов
                                назначения между текущим пунктом назначения и идентификатором
                                NavOptionsBuilder.popupto для последующего восстановления с помощью
                                NavOptionsBuilder.restoreState или атрибута restoreState с использованием того
                                же идентификатора NavOptionsBuilder.popupto (примечание: этот соответствующий идентификатор
                                имеет значение true независимо от того, является ли значение true или false).
                                 */
                            //saveState = true

                            //}

                            /*
                            Должно ли это действие навигации запускаться как одиночное верхнее
                            (т.е. в верхней части заднего стека будет не более одной копии
                            данного пункта назначения). Это работает аналогично тому,
                            как работает android.content.Намерение.FLAG_ACTIVITY_SINGLE_TOP
                            работает с действиями.
                             */
                            //launchSingleTop = true

                            /*
                            Должно ли это действие навигации восстанавливать какое-либо состояние,
                            ранее сохраненное с помощью всплывающего окна Builder.savestate или
                            атрибута popUpToSaveState. Если ранее ни одно состояние не было сохранено
                            с переходом по идентификатору назначения, это не имеет никакого эффекта.
                             */
                            //restoreState = true
                        }

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
                    heroesListviewModel = heroesListviewModel,
                    navController = navController
                )


            }
            composable(Screens.Hero.route + "/{id}") { navBackStack ->

                val id = navBackStack.arguments?.getString("id").toString()
                HeroScreen(
                    id,
                    heroViewModel,
                    navController,
                    onCheckedChange = { id,
                                        isChecked ->
                        heroViewModel.obtainEvent(
                            HeroEvent.OnFavoriteCLick(
                                id,
                                false
                            )
                        )

                    })
            }
            composable(Screens.Attr.route + "/{attr}") { navBackStack ->
                val attr = navBackStack.arguments?.getString("attr")
                AttributeScreen(attr!!, heroesListviewModel, navController)
            }
            composable(Screens.Favorites.route) {
                FavoritesScreen(heroesListviewModel, navController)
            }
            composable(Screens.Profile.route) {

                ProfileScreen(profileViewModel,{ id ->
                    profileViewModel.obtainEvent(ProfileEvent.OnSteamLogin(id))
                })

            }
        }


    }


}