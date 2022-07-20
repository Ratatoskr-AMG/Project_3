package ru.ratatoskr.project_3.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.presentation.screens.hero.models.HeroEvent
import ru.ratatoskr.project_3.presentation.screens.hero.models.HeroState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.utils.rememberForeverLazyListState
import ru.ratatoskr.project_3.presentation.theme.LoadingView
import ru.ratatoskr.project_3.presentation.theme.MessageView
import ru.ratatoskr.project_3.presentation.screens.hero.HeroViewModel
import ru.ratatoskr.project_3.presentation.screens.hero.views.HeroView

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
            HeroView(
                hero,
                navController,
                isChecked,
                onFavoriteChange = {
                    viewModel.obtainEvent(
                        HeroEvent.OnFavoriteCLick(
                            hero.id,
                            isChecked
                        )
                    )
                    /*onCheckedChange(hero.id, isChecked)*/
                },
                onRoleClick = {
                    navController.navigate(Screens.Role.route + "/" + it)
                }

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