package net.doheco.presentation.screens.recommendations

import androidx.compose.foundation.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.recommendations.models.RecommendationsState
import net.doheco.presentation.screens.recommendations.views.RecommendationsView
import net.doheco.presentation.theme.LoadingView
import net.doheco.presentation.theme.MessageView


@ExperimentalFoundationApi
@Composable
fun RecommendationsScreen(
    navController: NavController,
    viewModel: RecommendationsViewModel,
    widthSizeClass: WindowWidthSizeClass
) {

    when (val state = viewModel.recommendationsState.observeAsState().value) {
        is RecommendationsState.LoadedRecommendationsState<*> -> RecommendationsView(
            state.heroes,
            state.favoriteHeroes,
            state.player_tier,
            { navController.navigate(Screens.Hero.route + "/" + it.id) },
            { navController.navigate(Screens.Tier.route) },
            widthSizeClass
        )
        is RecommendationsState.LoadingRecommendationsState -> MessageView(stringResource(id = R.string.error_connection))
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroes()
        //viewModel.registerFirebaseEvent()
    })
}