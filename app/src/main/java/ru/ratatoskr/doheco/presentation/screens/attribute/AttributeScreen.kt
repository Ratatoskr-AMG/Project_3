package ru.ratatoskr.doheco.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.presentation.screens.Screens
import ru.ratatoskr.doheco.presentation.screens.attribute.AttributeViewModel
import ru.ratatoskr.doheco.presentation.screens.attribute.models.AttributeState
import ru.ratatoskr.doheco.presentation.screens.attribute.views.HeroesByAttributeListView
import ru.ratatoskr.doheco.presentation.theme.LoadingView
import ru.ratatoskr.doheco.presentation.theme.MessageView

@ExperimentalFoundationApi
@Composable
fun AttributeScreen(
    attr: String,
    id: String,
    viewModel: AttributeViewModel,
    navController: NavController
) {
    val viewState = viewModel.heroesListState.observeAsState()

    when (val state = viewState.value) {
        is AttributeState.LoadedHeroesState<*> -> HeroesByAttributeListView(
            attr,
            id,
            state.heroes,
            state.isSortAsc,
            navController,
            {
                navController.navigate(Screens.Hero.route + "/" + it.id)
            },
            {
                android.util.Log.e("TOHA_test","when")
                viewModel.switchAttrSortDirection(attr,it)
            })
        is AttributeState.NoHeroesState -> MessageView(stringResource(id = R.string.heroes_not_found))
        is AttributeState.LoadingHeroesState -> LoadingView(stringResource(id = R.string.loading))
        is AttributeState.ErrorHeroesState -> MessageView(stringResource(id = R.string.error))
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesByAttr(attr)
    })
}