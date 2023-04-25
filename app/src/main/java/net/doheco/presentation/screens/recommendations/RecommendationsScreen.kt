package net.doheco.presentation.screens.recommendations

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.models.ProfileEvent
import net.doheco.presentation.screens.recommendations.models.RecommendationsState
import net.doheco.presentation.screens.recommendations.views.RecommendationsView
import net.doheco.presentation.screens.recommendations.views.RecommendationsViewLoading


@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
fun RecommendationsScreen(
    navController: NavController,
    recommendationsViewModel: RecommendationsViewModel,
    profileViewModel: ProfileViewModel,
    widthSizeClass: WindowWidthSizeClass
) {

    val viewState = recommendationsViewModel.recommendationsState.observeAsState()
    val openUpdateDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val refreshing = viewState.value is RecommendationsState.RefreshingRecommendationsState
    val refresh: () -> Unit = {
        coroutineScope.launch{
            recommendationsViewModel.setRefreshingState()
            recommendationsViewModel.obtainEvent(ProfileEvent.OnUpdate())
        }
    }

    val refreshState = rememberPullRefreshState(refreshing, refresh)

    Box(
        modifier = Modifier
            .pullRefresh(refreshState)
            .padding()
            .background(Color.White),
    ){
        when (viewState.value) {
            is RecommendationsState.LoadedRecommendationsState<*> -> RecommendationsView(
                (viewState.value as RecommendationsState.LoadedRecommendationsState<*>).heroes,
                (viewState.value as RecommendationsState.LoadedRecommendationsState<*>).favoriteHeroes,
                (viewState.value as RecommendationsState.LoadedRecommendationsState<*>).player_tier,
                { navController.navigate(Screens.Hero.route + "/" + it.id) },
                { navController.navigate(Screens.Tier.route) },
                widthSizeClass,
                onReloadClick = {
                    recommendationsViewModel.obtainEvent(ProfileEvent.OnUpdate())
                    Log.d("META","METAonReloadClick");
                    //recommendationsViewModel.getAllHeroes()
                })
            is RecommendationsState.LoadingRecommendationsState -> RecommendationsViewLoading(profileViewModel){
                recommendationsViewModel.obtainEvent(ProfileEvent.OnUpdate())

            }
            else -> {}
        }

        PullRefreshIndicator(
            refreshing = refreshing,
            state = refreshState,
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.TopCenter),
            backgroundColor = Color.White,
            contentColor = Black
        )
    }


    LaunchedEffect(key1 = Unit, block = {
        recommendationsViewModel.updateHeroesAndMatches()
        //viewModel.registerFirebaseEvent()
    })
}

