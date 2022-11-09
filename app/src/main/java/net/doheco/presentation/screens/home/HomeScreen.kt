package net.doheco.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.home.models.HomeState
import net.doheco.presentation.screens.home.views.HomeView
import net.doheco.presentation.theme.LoadingView
import net.doheco.presentation.theme.MessageView

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
    widthSizeClass: WindowWidthSizeClass
) {
    when (val state = viewModel.homeState.observeAsState().value) {
        is HomeState.LoadedHomeState<*> -> {
            //splashFun(false)
            Log.e("TOHARUS", "LoadedHomeState")
            HomeView(
                viewModel,
                viewModel.imageLoader,
                state.heroes,
                state.favoriteHeroes,
                {
                    navController.navigate(Screens.Hero.route + "/" + it.id)
                }, {
                    viewModel.getAllHeroesByStrSortByName(it)
                },
                widthSizeClass
            )
        }
        is HomeState.NoHomeState -> {
            Log.e("TOHARUS", "NoHomeState")
            MessageView(stringResource(id = R.string.heroes_not_found))
        }
        is HomeState.LoadingHomeState -> {
            Log.e("TOHARUS", "LoadingHomeState")
            LoadingBlack()
        }
        is HomeState.ErrorHomeState -> {
            Log.e("TOHARUS", "ErrorHomeState")
            MessageView(stringResource(id = R.string.error))
        }
        else -> {}
    }
//    LaunchedEffect(key1 = Unit, block = {
//        Log.e("WHAT", "launchedEffect")
//        viewModel.getAllHeroesSortByName()
//        //viewModel.registerFirebaseEvent()
//    })
}

@Composable
fun LoadingBlack() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {}
}