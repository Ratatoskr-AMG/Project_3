/*
Задачи
    Модуль клиент +
    Карточка героя +
    Размер картинок +
    "Двойное нажатие" в навигации +
    Карточка атрибута
    Перенос расчётов артибутов
    Избранные герои вместо Dashboard
    Профиль вместо Notifications
    Дизайн
К консультации:
    Архитектура (репозитории, юзкейсы и т.д.)
Книги:
    Карьера программиста крэкинг зэ пот
    Java Concurrency in Practiсe
*/

package ru.ratatoskr.project_3.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import ru.ratatoskr.project_3.presentation.screens.HeroScreen
import ru.ratatoskr.project_3.presentation.screens.HeroesListScreen
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel

sealed class Routes(val route: String) {
    object HeroesList : Routes("HeroesList")
    object Hero : Routes("Hero")
    object WaitScreen : Routes("WaitScreen")
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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
    object Home : Screens("home", R.string.title_home)
    object Hero : Screens("hero", R.string.title_hero)
    object Attr : Screens("hero", R.string.title_hero)
    object Dashboard : Screens("dashboard", R.string.title_dashboard)
    object Notifications : Screens("notifications", R.string.title_notifications)
}

@ExperimentalFoundationApi
@Composable
fun MainScreen(parentNavController: NavController) {
    val navController = rememberNavController()
    val items = listOf(Screens.Home, Screens.Dashboard, Screens.Notifications)

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
                val viewModel = hiltViewModel<HeroesListViewModel>()
                HeroesListScreen(viewModel = viewModel, navController = navController)
            }
            composable(Routes.Hero.route + "/{id}") { navBackStack ->
                val viewModel = hiltViewModel<HeroesListViewModel>()
                val id = navBackStack.arguments?.getString("id").toString()
                HeroScreen(id, viewModel, navController)
            }
            composable(Screens.Dashboard.route) { Text("Dashboard") }
            composable(Screens.Notifications.route) { Text("Notifications") }
        }
    }
}