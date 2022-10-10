package ru.ratatoskr.doheco.presentation.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.domain.utils.rememberForeverLazyListState
import ru.ratatoskr.doheco.presentation.screens.hero.models.HeroEvent
import ru.ratatoskr.doheco.presentation.screens.hero.models.HeroState
import ru.ratatoskr.doheco.presentation.base.Screens
import ru.ratatoskr.doheco.presentation.theme.LoadingView
import ru.ratatoskr.doheco.presentation.theme.MessageView
import ru.ratatoskr.doheco.presentation.screens.hero.HeroViewModel
import ru.ratatoskr.doheco.presentation.screens.hero.views.HeroView

@ExperimentalFoundationApi
@Composable
fun HeroScreen(
    id: String,
    viewModel: HeroViewModel,
    navController: NavController
) {

    val viewState = viewModel.heroState.observeAsState()


    when (val state = viewState.value) {
        is HeroState.HeroLoadedState -> {

            val isChecked = state.isFavorite
            val hero = state.hero
            val currentInfoBlock = state.currentInfoBlock
            val currentAttrsMax = state.currentAttrsMax
            var scrollState = rememberForeverLazyListState(key = "Hero_" + hero.localizedName)

            HeroView(
                viewModel,
                hero,
                isChecked,
                currentInfoBlock,
                currentAttrsMax,
                onFavoriteChange = {
                    viewModel.obtainEvent(
                        HeroEvent.OnFavoriteCLick(
                            hero.id,
                            isChecked
                        )
                    )
                },
                onRoleClick = {
                    navController.navigate(Screens.Role.route + "/" + it)
                },
                onAttrClick = {
                    Log.d("TOHA","onAttrClick")
                },
                onHeroInfoBlockSelect = {
                    Log.e("TOHA", "InfoblockName:"+it)
                    viewModel.obtainEvent(
                        HeroEvent.OnInfoBlockSelect(
                            it,
                            scrollState
                        )
                    )

                },
                navController,
                scrollState
            )
        }
        is HeroState.NoHeroState -> MessageView(stringResource(id = R.string.hero_not_found))
        is HeroState.LoadingHeroState -> LoadingView(stringResource(id = R.string.loading))
        is HeroState.ErrorHeroState -> MessageView(stringResource(id = R.string.error))
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getHeroById(id)
    })
}