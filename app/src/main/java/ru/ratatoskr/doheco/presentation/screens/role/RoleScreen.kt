package ru.ratatoskr.doheco.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.presentation.screens.role.RoleViewModel
import ru.ratatoskr.doheco.presentation.screens.role.models.RoleState
import ru.ratatoskr.doheco.presentation.screens.role.views.RoleListView
import ru.ratatoskr.doheco.presentation.theme.LoadingView
import ru.ratatoskr.doheco.presentation.theme.MessageView

@ExperimentalFoundationApi
@Composable
fun RoleScreen(
    role: String,
    viewModel: RoleViewModel,
    navController: NavController
) {
    val viewState = viewModel.roleState.observeAsState()


    when (val state = viewState.value) {

        is RoleState.LoadedHeroesListState<*> -> RoleListView(role, state.heroes, navController) {
            navController.navigate(Screens.Hero.route + "/" + it.id)
        }
        is RoleState.NoHeroesListState -> MessageView(stringResource(id = R.string.heroes_not_found))
        is RoleState.LoadingHeroesListState -> LoadingView(stringResource(id = R.string.loading))
        is RoleState.ErrorHeroesListState -> MessageView(stringResource(id = R.string.error))
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesByRole(role)
    })
}