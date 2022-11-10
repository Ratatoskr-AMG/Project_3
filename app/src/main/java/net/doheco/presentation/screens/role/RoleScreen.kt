package net.doheco.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.doheco.presentation.base.Screens
import net.doheco.presentation.screens.role.RoleViewModel
import net.doheco.presentation.screens.role.models.RoleState
import net.doheco.presentation.screens.role.views.RoleListView
import net.doheco.presentation.theme.LoadingView
import net.doheco.presentation.theme.MessageView
import net.doheco.R

@ExperimentalFoundationApi
@Composable
fun RoleScreen(
    role: String,
    viewModel: RoleViewModel,
    navController: NavController,
    widthSizeClass: WindowWidthSizeClass
) {
    val viewState = viewModel.roleState.observeAsState()


    when (val state = viewState.value) {

        is RoleState.LoadedHeroesListState<*> -> RoleListView(
            role,
            state.player_tier,
            state.heroes,
            state.favoriteHeroes,
            navController,
            { navController.navigate(Screens.Hero.route + "/" + it.id) },
            { navController.navigate(Screens.Tier.route) },
            widthSizeClass
        )
        is RoleState.NoHeroesListState -> MessageView(stringResource(id = R.string.heroes_not_found))
        is RoleState.LoadingHeroesListState -> LoadingView(stringResource(id = R.string.loading))
        is RoleState.ErrorHeroesListState -> MessageView(stringResource(id = R.string.error))
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesByRole(role)
    })
}