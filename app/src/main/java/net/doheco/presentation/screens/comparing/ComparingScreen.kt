package net.doheco.presentation.screens.comparing

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.doheco.R
import net.doheco.presentation.screens.comparing.models.ComparingEvent
import net.doheco.presentation.screens.comparing.models.ComparingState
import net.doheco.presentation.screens.comparing.views.ComparingView

@ExperimentalFoundationApi
@Composable
fun ComparingScreen(
    viewModel: ComparingViewModel,
    widthSizeClass: WindowWidthSizeClass
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
                state.currentInfoBlock,
                widthSizeClass
            )
        }
        is ComparingState.LoadingState -> {
            Log.e("TOHAht", "LoadingState")
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(

                        text = stringResource(id = R.string.wait),
                        color = Color.White,
                        modifier = Modifier.padding(start = 50.dp, end = 50.dp ),
                        textAlign = TextAlign.Center
                    )
                }
         }
        else -> {

        }
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.setHeroesState()
    })
}


