package ru.ratatoskr.doheco.presentation.screens.comparing

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import ru.ratatoskr.doheco.presentation.screens.comparing.models.ComparingEvent
import ru.ratatoskr.doheco.presentation.screens.comparing.models.ComparingState
import ru.ratatoskr.doheco.presentation.screens.comparing.views.ComparingView

@ExperimentalFoundationApi
@Composable
fun ComparingScreen(
    viewModel: ComparingViewModel
) {
    val viewState = viewModel.comparingState.observeAsState()
    var selectMode by remember { mutableStateOf(false) }
    var leftSelected by remember { mutableStateOf(false) }
    var rightSelected by remember { mutableStateOf(false) }

    when (val state = viewState.value) {
        is ComparingState.HeroesState -> {
            var left by remember { mutableStateOf(state.left) }
            var right by remember { mutableStateOf(state.right) }
            ComparingView(
                onLeftClick = {
                    rightSelected = false
                    selectMode = true
                    leftSelected = true
                },
                onRightClick = {
                    leftSelected = false
                    selectMode = true
                    rightSelected = true
                },
                onHeroClick = {
                    if (leftSelected) {
                        left = it
                        viewModel.setLeftSelectedComparingHeroToSP(it.id.toString())
                    }
                    if (rightSelected) {
                        right = it
                        viewModel.setRightSelectedComparingHeroToSP(it.id.toString())
                    }
                    selectMode = false
                    leftSelected = false
                    rightSelected = false
                },
                {
                    viewModel.obtainEvent(
                        ComparingEvent.OnInfoBlockSelect(
                           it
                        )
                    )
                },
                selectMode,
                leftSelected,
                rightSelected,
                left,
                right,
                state.heroes,
                state.favoriteHeroes,
                state.currentInfoBlock
            )
        }
        is ComparingState.LoadingState -> {
            Log.e("TOHAht", "LoadingState")
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.setHeroesState()
    })
}


