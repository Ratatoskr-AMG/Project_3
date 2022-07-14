package ru.ratatoskr.project_3.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import ru.ratatoskr.project_3.presentation.screens.attribute.AttributeViewModel
import ru.ratatoskr.project_3.presentation.screens.attribute.models.AttributeState
import ru.ratatoskr.project_3.presentation.screens.attribute.views.HeroesByAttributeListView
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView

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
        is AttributeState.NoHeroesState -> MessageView("Heroes not found")
        is AttributeState.LoadingHeroesState -> LoadingView("Heroes loading...")
        is AttributeState.ErrorHeroesState -> MessageView("Heroes error!")
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllHeroesByAttr(attr)
    })
}