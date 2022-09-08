package ru.ratatoskr.doheco.presentation.screens.recommendations

import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.presentation.screens.Screens
import ru.ratatoskr.doheco.presentation.screens.recommendations.models.RecommendationsState
import ru.ratatoskr.doheco.presentation.screens.recommendations.views.RecommendationsView
import ru.ratatoskr.doheco.presentation.theme.LoadingView


@ExperimentalFoundationApi
@Composable
fun RecommendationsScreen(
    navController: NavController,
    viewModel: RecommendationsViewModel
) {

    when (val state = viewModel.recommendationsState.observeAsState().value) {
        is RecommendationsState.LoadedRecommendationsState<*> -> RecommendationsView(
            viewModel,
            viewModel.imageLoader,
            state.heroes,
            state.player_tier, {
               navController.navigate(Screens.Hero.route + "/" + it.id)
            }, {
                navController.navigate(Screens.Tier.route)
            }
        )
        is RecommendationsState.LoadingRecommendationsState -> LoadingView(stringResource(id = R.string.loading))
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroes()
        //viewModel.registerFirebaseEvent()
    })
}