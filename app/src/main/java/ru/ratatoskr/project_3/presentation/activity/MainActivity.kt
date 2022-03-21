package ru.ratatoskr.project_3.presentation.activity

import android.net.Uri
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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.*
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.domain.helpers.states.VideoState
import ru.ratatoskr.project_3.presentation.screens.*
import ru.ratatoskr.project_3.presentation.viewmodels.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {


    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }

    }

}

fun stopPlayer(state: State<VideoState?>){
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
    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .background(Color.Gray)
                    .height(35.dp),
                backgroundColor = Color.White
            ) {
                val items = listOf(Screens.Home, Screens.Favorites, Screens.Profile, Screens.Video)
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