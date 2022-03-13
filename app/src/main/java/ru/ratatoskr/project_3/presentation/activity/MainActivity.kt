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
import ru.ratatoskr.project_3.presentation.screens.HeroesListScreen
import ru.ratatoskr.project_3.presentation.viewmodels.HeroEvent
import ru.ratatoskr.project_3.presentation.viewmodels.HeroViewModel
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val context = this
            NavHost(navController = navController, startDestination = "main") {
                composable("main") { MainScreen(navController, context) }
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
fun MainScreen(parentNavController: NavController, context: AppCompatActivity) {
    val navController = rememberNavController()
    val items = listOf(Screens.Home, Screens.Favorites, Screens.Profile)
    val heroesListviewModel = hiltViewModel<HeroesListViewModel>()
    val heroViewModel = hiltViewModel<HeroViewModel>()
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

                @Composable
                fun loadSuceed(id: String) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                    Text(id)
                    }

                }
                @Composable
                fun loadWebUrl(url: String) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        AndroidView(factory = {
                            WebView(context).apply {

                                webViewClient = object : WebViewClient() {
                                    override fun onPageStarted(view:WebView?, url:String?, favicon: Bitmap?){
                                        val Url: Uri = Uri.parse(url)
                                        if(Url.authority.equals("ratatoskr.ru")){
                                            val userAccountUrl =
                                                Uri.parse(Url.getQueryParameter("openid.identity"))
                                            val userId = userAccountUrl.lastPathSegment
                                            Toast.makeText(context, "Your userId is: "+userId, Toast.LENGTH_LONG).show()
                                            Log.e("TOHA","userId:"+userId)
                                            /*
                                            http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=D076D1B0AD4391F8156F8EED08C597CE&steamids=76561198165608798
                                             */
                                        }
                                    }

                                }
                                settings.javaScriptEnabled = true
                                loadUrl(url)

                            }

                        }, modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight())
                    }
                }

                val url = "https://steamcommunity.com/openid/login?" +
                        "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
                        "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
                        "openid.mode=checkid_setup&" +
                        "openid.ns=http://specs.openid.net/auth/2.0&" +
                        "openid.realm=http://ratatoskr.ru&" +
                        "openid.return_to=http://ratatoskr.ru/steam_success"

                loadWebUrl(url)


            }


        }


    }


}