package ru.ratatoskr.project_3.presentation.activity

import android.os.Bundle
import android.util.Log
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.MyComposable
import ru.ratatoskr.project_3.presentation.viewmodel.MainViewModel
import ru.alexgladkov.odyssey.compose.AndroidScreenHost
import ru.alexgladkov.odyssey.compose.extensions.bottomNavigation
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.helpers.BottomItemModel
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.tab
import ru.alexgladkov.odyssey.compose.setupActivityWithRootController
import ru.ratatoskr.project_3.presentation.OdysseyComposable

data class DetailParams(val title: String)
data class DialogParams(val value: String)

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    private fun buildComposeNavigationGraph(): RootComposeBuilder.() -> Unit {
        return { generateGraph() }
    }

    fun RootComposeBuilder.generateGraph() {

        var OdysseyComposable = OdysseyComposable()

        /*
        screen(NavigationTree.Root.Splash.name) {
            OdysseyComposable.LoginScreen(rootController)
        }

         */

        flow(name = NavigationTree.Root.Auth.toString()) {
            screen(NavigationTree.Auth.Onboarding.toString()) {
                OdysseyComposable.OnboardingScreen(rootController)
            }

            screen(NavigationTree.Auth.Login.toString()) {
                OdysseyComposable.LoginScreen(rootController, params as? String)
            }

            screen(NavigationTree.Auth.TwoFactor.toString()) {
                OdysseyComposable.LoginCodeScreen(rootController, params as? String)
            }
        }

        bottomNavigation(
            name = NavigationTree.Root.Main.toString(),
            bottomItemModels = listOf(
                BottomItemModel(title = "Main"),
                BottomItemModel(title = "Favorite"),
                BottomItemModel(title = "Settings")
            )
        ) {
            tab(NavigationTree.Tabs.Main.toString()) {
                screen(NavigationTree.Main.Feed.toString()) {
                    OdysseyComposable.FeedScreen(rootController)
                }

                screen(NavigationTree.Main.Detail.toString()) {
                    OdysseyComposable.DetailScreen(rootController, param = params as DetailParams)
                }
            }

            tab(NavigationTree.Tabs.Favorite.toString()) {
                screen(NavigationTree.Favorite.Flow.toString()) {
                    OdysseyComposable.FavoriteScreen(rootController)
                }
            }

            tab(NavigationTree.Tabs.Settings.toString()) {
                screen(NavigationTree.Settings.Profile.toString()) {
                    OdysseyComposable.ProfileScreen()
                }
            }
        }

        screen(NavigationTree.Root.Dialog.toString()) {
            OdysseyComposable.DialogScreen(rootController, params = params as DialogParams)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidScreenHost(this)
            .setupActivityWithRootController(
                startScreen = NavigationTree.Root.Splash.toString(),
                block = buildComposeNavigationGraph()
            )

        /*

        var myComposable = MyComposable()
        setContent {
            myComposable.Wrapper { myComposable.w8() }
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.HeroesList.observe(this) {
            if (viewModel.HeroesList.value != null) {
                viewModel.updateAllHeroesTable(this, viewModel.HeroesList.value!!)
                setContent {
                    var heroesList: List<Hero> = remember { viewModel.HeroesList.value!! }
                    myComposable.Wrapper {myComposable.Heroes(heroesList)
                    }
                }
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
            try {
                viewModel.getAllHeroesList()
            } catch (exception: Exception) {
                Log.d("TOHA", "exception:" + exception.toString())
            }
        }

        */
    }
}

object NavigationTree {
    enum class Root {
        Splash, Auth, Main, Dialog
    }

    enum class Auth {
        Onboarding, Login, TwoFactor
    }

    enum class Main {
        Feed, Detail
    }

    enum class Favorite {
        Flow
    }

    enum class Settings {
        Profile
    }

    enum class Tabs {
        Main, Favorite, Settings
    }
}