package ru.ratatoskr.project_3.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.composable.MyComposable
import ru.ratatoskr.project_3.presentation.viewmodel.MainViewModel

sealed class Routes(val route: String) {
    object HeroesList : Routes("HeroesList")
    object Hero : Routes("Hero")
    object WaitScreen : Routes("WaitScreen")
}

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel(repository = HeroesRepoImpl())
    var myComposable = MyComposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            myComposable.Wrapper { myComposable.WaitScreen() }
        }

        viewModel.HeroesList.observe(this) {
            if (viewModel.HeroesList.value != null) {
                setContent {
                    val navController = rememberNavController()
                    var heroesList: List<Hero> = remember { viewModel.HeroesList.value!! }
                    NavHost(navController, startDestination = Routes.HeroesList.route){
                        composable(Routes.HeroesList.route){
                            myComposable.Wrapper {
                                myComposable.HeroesScreen(heroesList,navController)
                            }
                        }
                        composable(Routes.WaitScreen.route){
                            myComposable.Wrapper {
                                myComposable.WaitScreen()
                            }
                        }
                        composable(Routes.Hero.route+"/{id}"){ navBackStack ->
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