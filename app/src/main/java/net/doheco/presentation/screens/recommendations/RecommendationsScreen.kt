package net.doheco.presentation.screens.recommendations

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.models.ProfileEvent
import net.doheco.presentation.screens.recommendations.models.RecommendationsState
import net.doheco.presentation.screens.recommendations.views.RecommendationsView
import net.doheco.presentation.theme.MessageView


@ExperimentalFoundationApi
@Composable
fun RecommendationsScreen(
    navController: NavController,
    recommendationsViewModel: RecommendationsViewModel,
    profileViewModel: ProfileViewModel,
    widthSizeClass: WindowWidthSizeClass
) {
    val openUpdateDialog = remember { mutableStateOf(false) }
    when (val state = recommendationsViewModel.recommendationsState.observeAsState().value) {
        is RecommendationsState.LoadedRecommendationsState<*> -> RecommendationsView(
            state.heroes,
            state.favoriteHeroes,
            state.player_tier,
            { navController.navigate(Screens.Hero.route + "/" + it.id) },
            { navController.navigate(Screens.Tier.route) },
            widthSizeClass,
            onReloadClick = {
                recommendationsViewModel.obtainEvent(ProfileEvent.OnUpdate)
                Log.d("META","METAonReloadClick");
                recommendationsViewModel.getAllHeroes()
            })
        is RecommendationsState.LoadingRecommendationsState -> MessageView(stringResource(id = R.string.wait))
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {
        recommendationsViewModel.getAllHeroes()
        //viewModel.registerFirebaseEvent()
    })
}