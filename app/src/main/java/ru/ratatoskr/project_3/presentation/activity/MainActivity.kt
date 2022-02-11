package ru.ratatoskr.project_3.presentation.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.composable.MyComposable
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel

sealed class Screens(val route: String, val stringId: Int) {
    object HeroesList: Screens("home", R.string.title_heroesList)
    object Dashboard: Screens("dashboard", R.string.title_dashboard)
    object Notifications: Screens("notifications", R.string.title_notifications)
    object Hero: Screens("hero", R.string.title_hero)
}
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel = HeroesListViewModel(repository = HeroesRepoImpl())
    var myComposable = MyComposable()
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val navController = rememberNavController()
            MainScreen(navController)
        }


        setContent {
            myComposable.Wrapper { myComposable.WaitScreen() }
        }

        viewModel.HeroesList.observe(this) {
            if (viewModel.HeroesList.value != null) {
                setContent {
                    val navController = rememberNavController()
                    var heroesList: List<Hero> = remember { viewModel.HeroesList.value!! }
                    NavHost(navController, startDestination = Screens.HeroesList.route){
                        composable(Screens.HeroesList.route){
                            myComposable.Wrapper {
                                myComposable.HeroesScreen(heroesList,navController)
                            }
                        }
                        //composable(Routes.WaitScreen.route){
                        //    myComposable.Wrapper {
                        //        myComposable.WaitScreen()
                        //    }
                        //}
                        composable(Screens.Hero.route+"/{id}"){ navBackStack ->
                            val id = navBackStack.arguments?.getString("id").toString()
                            myComposable.Wrapper {
                                myComposable.HeroScreen(id)
                            }
                        }
                    }
                }
            }
        }

        viewModel.getListHeroesFromAPI(this)


    }
}

@ExperimentalFoundationApi
@Composable
fun MainScreen(parentNavController: NavController) {
    val navController = rememberNavController()
    val items = listOf(Screens.Hero, Screens.Dashboard, Screens.Notifications)
    var myComposable = MyComposable()

    Scaffold(
        bottomBar= {

        }
    ){
        NavHost(navController, startDestination = Screens.Hero.route){
            composable(Screens.HeroesList.route){

                val viewModel = hiltViewModel<HeroesListViewModel>()
                CarryScreen(viewModel = viewModel, navController = navController)

                myComposable.Wrapper {
                    myComposable.HeroesScreen(heroesList,navController)
                }
            }
            composable(Screens.Hero.route+"/{id}"){ navBackStack ->
                val id = navBackStack.arguments?.getString("id").toString()
                myComposable.Wrapper {
                    myComposable.HeroScreen(id)
                }
            }
            composable(Screens.Dashboard.route) { Text("Dashboard") }
            composable(Screens.Notifications.route) { Text("Notifications") }
        }
    }
}